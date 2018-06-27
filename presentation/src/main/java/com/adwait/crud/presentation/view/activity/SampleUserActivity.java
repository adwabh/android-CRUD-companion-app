package com.adwait.crud.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.adwait.crud.presentation.R;
import com.adwait.crud.presentation.internal.di.HasComponent;
import com.adwait.crud.presentation.internal.di.components.DaggerSampleUserListComponent;
import com.adwait.crud.presentation.internal.di.components.SampleUserListComponent;
import com.adwait.crud.presentation.model.SampleUserList;
import com.adwait.crud.presentation.presenter.SampleUserListPresenter;
import com.adwait.crud.presentation.utils.Constant;
import com.adwait.crud.presentation.view.SampleUserListView;
import com.adwait.crud.presentation.view.adapter.SampleUserListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class SampleUserActivity extends BaseActivity implements HasComponent<SampleUserListComponent>, SwipeRefreshLayout.OnRefreshListener, SampleUserListView, View.OnClickListener {

    private static final int REQUEST_ADD_USER = 212;
    private RecyclerView recyclerView_user;
    private FloatingActionButton floatingActionButton_add;
    private SwipeRefreshLayout swipeToRefreshLayout;
    private SampleUserListAdapter adapter;

    @Inject
    protected SampleUserListPresenter presenter;
    private SampleUserListComponent sampleUserListComponent;
    private LinearLayoutManager layoutManager;
    private RelativeLayout relativeLayout_root;
    private int fabY;
    private int fabX;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SampleUserActivity.class);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_list);
        relativeLayout_root = findViewById(R.id.relativeLayout_root);
        recyclerView_user = findViewById(R.id.recyclerView_user);
        floatingActionButton_add = findViewById(R.id.floatingActionButton_add);
        swipeToRefreshLayout = findViewById(R.id.swipeToRefreshLayout);
    }

    @Override
    protected void initData(Bundle savedInstanceState) throws Exception {
        sampleUserListComponent = DaggerSampleUserListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        sampleUserListComponent.present(this);
        adapter = new SampleUserListAdapter();
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView_user.setLayoutManager(layoutManager);
        recyclerView_user.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        swipeToRefreshLayout.setOnRefreshListener(this);
        floatingActionButton_add.setOnClickListener(this);
        relativeLayout_root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            relativeLayout_root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            relativeLayout_root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        // measure your views here
                        int[] location = new int[2];
                        floatingActionButton_add.getLocationOnScreen(location);
                        fabX = location[0];
                        fabY = location[1];
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setSampleUserListView(this);
        presenter.getSampleUserList();
    }

    @Override
    public void onRefresh() {
        presenter.getSampleUserList();
    }

    @Override
    public SampleUserListComponent getComponent() {
        return null;
    }

    @Override
    public void onSampleUserListFetched(SampleUserList sampleUserList) {
        try {
            SampleUserList.SampleUserListData data;
            ArrayList<SampleUserList.SampleUser> list;
            if (sampleUserList!=null) {
                data = sampleUserList.getData();
                if (data!=null) {
                    list = data.getUser_list();
                    adapter.setUserList(list);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        try {
            if (swipeToRefreshLayout!=null) {
                swipeToRefreshLayout.setRefreshing(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading() {
        try {
            if (swipeToRefreshLayout!=null) {
                swipeToRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Snackbar.make(recyclerView_user,message,Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public Context context() {
        return this;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_ADD_USER == requestCode){
            if(RESULT_OK == resultCode){
                SampleUserList.SampleUser newUser = new SampleUserList.SampleUser();
                newUser.setUsername(data.getStringExtra(Constant.ADD_USERNAME));
                newUser.setEmail(data.getStringExtra(Constant.ADD_EMAIL));
                presenter.addUser(newUser);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.floatingActionButton_add:
                AddSampleUserActivity.show(this, REQUEST_ADD_USER, fabX,fabY);
                break;
        }
    }
}
