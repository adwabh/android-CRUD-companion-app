package com.adwait.crud.presentation.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.adwait.crud.presentation.R;


/**
 * Created by adwait on 21/12/17.
 */

public class SplashScreenActivity extends BaseActivity {
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler(getMainLooper());
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNextActivity();
            }
        },SPLASH_SCREEN_DELAY);
    }

    private void startNextActivity() {
//       launchActivity(ZXingFullScannerActivity.class);
       launchActivity(SampleUserActivity.class);
    }

    public void launchActivity(Class<?> clss) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            mClss = clss;
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
//        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
            finish();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Snackbar.make(getWindow().getDecorView(), "Please grant camera permission to use the QR Scanner", Snackbar.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
