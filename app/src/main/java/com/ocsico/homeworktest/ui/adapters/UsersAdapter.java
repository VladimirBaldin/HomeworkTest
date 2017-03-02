package com.ocsico.homeworktest.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ocsico.homeworktest.R;
import com.ocsico.homeworktest.model.Owner;
import com.ocsico.homeworktest.model.User;
import com.ocsico.homeworktest.ui.MapActivity;
import com.ocsico.homeworktest.util.Constants;

import java.util.List;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> mUsers;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mRootView;
        public TextView mNameView;
        public TextView mSurnameView;
        public ImageView mPhoto;

        public ViewHolder(View v) {
            super(v);
            mRootView = v;
            mNameView = (TextView) v.findViewById(R.id.tv_name);
            mSurnameView = (TextView) v.findViewById(R.id.tv_surname);
            mPhoto = (ImageView) v.findViewById(R.id.iv_foto);
        }
    }

    public UsersAdapter(List<User> users) {
        mUsers = users;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context context = holder.mRootView.getContext();
        final User user = mUsers.get(position);
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra(Constants.EXTRA_USER_ID, user.userid);
                context.startActivity(intent);
            }
        });
        Owner owner = user.owner;
        holder.mNameView.setText(owner.name);
        holder.mSurnameView.setText(owner.surname);
        if (!TextUtils.isEmpty(owner.foto)) {
            ImageLoader.getInstance().displayImage(owner.foto, holder.mPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}