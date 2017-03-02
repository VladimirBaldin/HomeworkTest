package com.ocsico.homeworktest.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.ocsico.homeworktest.R;
import com.ocsico.homeworktest.net.model.SnappedPoint;
import com.ocsico.homeworktest.net.model.SnappedPoints;
import com.ocsico.homeworktest.model.Vehicle;
import com.ocsico.homeworktest.net.requests.SnapToRoadsSpiceRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.baldin on 01.03.2017.
 */

/**
 * Adapter to show info window for vehicles on map.
 */
public class VehicleInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context mContext;
    private List<Vehicle> mVehicles;
    private Marker mMarkerShowingInfoWindow;
    private SpiceManager mSpiceManager;
    private VehicleInfoListener mListener;

    public interface VehicleInfoListener {
        void onRoadLoaded(List<SnappedPoint> points);
        Location getMyLocation();
    }

    public VehicleInfoWindowAdapter(Context context, List<Vehicle> vehicles, SpiceManager spiceManager, VehicleInfoListener listener) {
        mContext = context;
        mVehicles = vehicles;
        mSpiceManager = spiceManager;
        mListener = listener;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vehicle_info, null, false);

        mMarkerShowingInfoWindow = marker;
        final LatLng position = marker.getPosition();

        // get vehicle for this marker
        double lat = position.latitude;
        double lon = position.longitude;
        Vehicle vehicle = null;
        for (Vehicle v : mVehicles) {
            if (v.lat == lat && v.lon == lon) {
                vehicle = v;
                break;
            }
        }

        if (vehicle != null) {
            ((TextView) view.findViewById(R.id.tv_name)).setText(vehicle.getName());
            view.findViewById(R.id.view_color).setBackgroundColor(vehicle.getColor());
            final ImageView iv = (ImageView) view.findViewById(R.id.iv_vehicle);
            ImageLoader.getInstance().displayImage(vehicle.foto, iv);
            List<Bitmap> bitmaps = MemoryCacheUtils.findCachedBitmapsForImageUri(vehicle.foto, ImageLoader.getInstance().getMemoryCache());
            if (bitmaps.size() == 0) {
                ImageLoader.getInstance().loadImage(vehicle.foto, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        LatLng infoPosition = mMarkerShowingInfoWindow.getPosition();
                        if (position.equals(infoPosition)) {
                            mMarkerShowingInfoWindow.hideInfoWindow();
                            mMarkerShowingInfoWindow.showInfoWindow();
                        }
                    }
                });
            } else {
                iv.setImageBitmap(bitmaps.get(0));
            }
            // request the nearest road to the vehicle
            List<LatLng> plots = new ArrayList<LatLng>();
            Location me = mListener.getMyLocation();
//            if (me != null)
//                plots.add(new LatLng(me.getLatitude(), me.getLongitude()));
            plots.add(new LatLng(56.95, 24.12)); // for testing
            plots.add(vehicle.getLocation());
            requestRoad(plots);
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void requestRoad(final List<LatLng> plots) {
        String path = createRoadsPath(plots);
        SnapToRoadsSpiceRequest spiceRequest = new SnapToRoadsSpiceRequest(path, true);
        mSpiceManager.execute(spiceRequest, new RequestListener<SnappedPoints>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
            }

            @Override
            public void onRequestSuccess(final SnappedPoints result) {
                if (mListener != null) {
                    mListener.onRoadLoaded(result.snappedPoints);
                }
            }

        });
    }

    private String createRoadsPath(List<LatLng> plots) {
        StringBuilder path = new StringBuilder();
        if (plots.size() > 0) {
            for (int i = plots.size() - 1; i >= 0; i--) {
                path.append(plots.get(i).latitude).append(",").append(plots.get(i).longitude);
                if (i != 0) {
                    path.append("|");
                }
            }
        }
        return path.toString();
    }
}
