package com.ocsico.homeworktest.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ocsico.homeworktest.R;
import com.ocsico.homeworktest.db.DbHelper;
import com.ocsico.homeworktest.model.User;
import com.ocsico.homeworktest.net.model.UserList;
import com.ocsico.homeworktest.net.BaseRequestListener;
import com.ocsico.homeworktest.net.requests.GetUsersRequest;
import com.ocsico.homeworktest.ui.adapters.UsersAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.List;

/**
 * Created by v.baldin on 01.03.2017.
 */

public class UsersActivity extends AbsActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mRecyclerView = (RecyclerView) findViewById(R.id.view_users);
        mProgressView = (ProgressBar) findViewById(R.id.progress);

//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetUsersRequest request = new GetUsersRequest();
        getSpiceManager().execute(request, mUsersRequestListener);
    }

    private void updateUsers(List<User> users) {
        mAdapter = new UsersAdapter(users);
        mRecyclerView.setAdapter(mAdapter);
    }

    private BaseRequestListener<UserList> mUsersRequestListener = new BaseRequestListener<UserList>(this) {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            super.onRequestFailure(spiceException);
            hideProgress();
        }

        @Override
        public void onRequestSuccess(final UserList result) {
            List<User> users = DbHelper.getUsers();
            hideProgress();
            if (users != null) {
                updateUsers(users);
            }
        }
    };

    private void showProgress() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mProgressView.setVisibility(View.GONE);
    }
}
