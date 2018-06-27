package com.adwait.crud.presentation.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.adwait.crud.presentation.R;
import com.adwait.crud.presentation.AndroidApplication;
import com.adwait.crud.presentation.internal.di.components.ApplicationComponent;
import com.adwait.crud.presentation.internal.di.modules.ActivityModule;
import com.adwait.crud.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getApplicationComponent().inject(this);
        initView();
        try {
            initData(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initEvent();
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    protected void initView() {

    }

    protected void initData(Bundle savedInstanceState) throws Exception {

    }

    protected void initEvent() {

    }

    public void setToolbar(boolean isShowTitle, int logo_with_icon, boolean isHomeUpEnabled, String toolbar_title_text) {
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
//        toolbar.setContentInsetsAbsolute(0, 0);

        setSupportActionBar(toolbar);
        try {
            if (isHomeUpEnabled) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(toolbar_title_text);

            } else {
                if (isShowTitle) {
                    getSupportActionBar().setTitle(toolbar_title_text);
                } else {
                    toolbar.setLogo(logo_with_icon);
                    getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
