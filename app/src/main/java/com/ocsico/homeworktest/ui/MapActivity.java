package com.ocsico.homeworktest.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ocsico.homeworktest.R;
import com.ocsico.homeworktest.db.DbHelper;
import com.ocsico.homeworktest.net.BaseRequestListener;
import com.ocsico.homeworktest.net.model.SnappedPoint;
import com.ocsico.homeworktest.model.Vehicle;
import com.ocsico.homeworktest.net.model.VehiclePosition;
import com.ocsico.homeworktest.net.model.VehiclePositions;
import com.ocsico.homeworktest.net.GoogleRetrofitSpiceService;
import com.ocsico.homeworktest.net.requests.GetVehiclePositionsRequest;
import com.ocsico.homeworktest.ui.adapters.VehicleInfoWindowAdapter;
import com.ocsico.homeworktest.util.Constants;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapActivity extends AbsActivity implements OnMapReadyCallback, VehicleInfoWindowAdapter.VehicleInfoListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private final long POSITION_UPDATE_TIMEOUT = 60 * 1000;// in milliseconds
    private final int REQUEST_FINE_LOCATION = 101;

    private GoogleMap mMap;
    private long mUserId;
    private Handler mHandler = new Handler();
    private SpiceManager mGoogleRoadsSpiceManager = new SpiceManager(GoogleRetrofitSpiceService.class);
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdates = true;
    private Location mMyLocation;
    private List<Marker> mVehicles = new ArrayList<>();
    private Polyline mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = getIntent().getLongExtra(Constants.EXTRA_USER_ID, -1);
        setContentView(R.layout.activity_map);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        mGoogleRoadsSpiceManager.start(this);
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleRoadsSpiceManager.shouldStop();
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mHandler.post(mPositionUpdateRunnable);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onRoadLoaded(List<SnappedPoint> points) {
        PolylineOptions opts = new PolylineOptions();
        for (SnappedPoint point : points) {
            opts.add(point.getLatLng());
        }
        clearPath();
        mPath = mMap.addPolyline(opts);
    }

    @Override
    public Location getMyLocation() {
        return mMyLocation;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_FINE_LOCATION);

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(), this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onLocationChanged(Location location) {
        mMyLocation = new Location(location);
    }

    private BaseRequestListener<VehiclePositions> mVehiclesRequestListener = new BaseRequestListener<VehiclePositions>(this) {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mHandler.postDelayed(mPositionUpdateRunnable, POSITION_UPDATE_TIMEOUT);
        }

        @Override
        public void onRequestSuccess(VehiclePositions result) {
            if (result != null) {
                ArrayList<VehiclePosition> vehicles = result.data;
                // remove incorrect data
                Iterator<VehiclePosition> iterator = vehicles.iterator();
                while (iterator.hasNext()) {
                    VehiclePosition position = iterator.next();
                    if (!position.isCorrect())
                        iterator.remove();
                }

                for (VehiclePosition position : result.data) {
                    DbHelper.updateVehicle(position);
                }

                displayVehicles();
            }
            mHandler.postDelayed(mPositionUpdateRunnable, POSITION_UPDATE_TIMEOUT);
        }
    };

    private void displayVehicles() {
        clearVehicles();
        List<Vehicle> vehicles = DbHelper.getVehicles(mUserId);
        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            LatLng location = new LatLng(vehicle.lat, vehicle.lon);
            mVehicles.add(mMap.addMarker(new MarkerOptions().position(location)));
            if (!iterator.hasNext()) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
            }
            mMap.setInfoWindowAdapter(new VehicleInfoWindowAdapter(this, vehicles, mGoogleRoadsSpiceManager, this));
        }
    }

    private void clearVehicles() {
        for (Marker m : mVehicles)
            m.remove();
        mVehicles.clear();
    }

    private void clearPath() {
        if (mPath != null)
            mPath.remove();
        mPath = null;
    }

    private Runnable mPositionUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            GetVehiclePositionsRequest request = new GetVehiclePositionsRequest(mUserId);
            getSpiceManager().execute(request, mVehiclesRequestListener);
        }
    };
}
