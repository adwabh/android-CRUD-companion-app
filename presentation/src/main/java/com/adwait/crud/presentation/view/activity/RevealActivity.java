package com.adwait.crud.presentation.view.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

/**
 * https://stackoverflow.com/a/38185482/3530647
 * */
public abstract class RevealActivity extends BaseActivity {

    private View revealView;

    public static final String REVEAL_X="REVEAL_X";
    public static final String REVEAL_Y="REVEAL_Y";

    public void showRevealEffect(Bundle savedInstanceState, final View rootView) {

        revealView=rootView;

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();

            if(viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        circularRevealActivity(rootView);

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                    }
                });
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity(View rootView) {

        int cx = getIntent().getIntExtra(REVEAL_X, 0);
        int cy = getIntent().getIntExtra(REVEAL_Y, 0);

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
        circularReveal.setDuration(400);

        // make the view visible and start the animation
        rootView.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: onBackPressed();
                break;
                default:
            return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        destroyActivity(revealView);
    }

    private void destroyActivity(View rootView) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            destroyCircularRevealActivity(rootView);
        else
            finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void destroyCircularRevealActivity(final View rootView) {
        int cx = getIntent().getIntExtra(REVEAL_X, 0);
        int cy = getIntent().getIntExtra(REVEAL_Y, 0);

        float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, finalRadius, 0);
        circularReveal.setDuration(400);

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rootView.setVisibility(View.INVISIBLE);
                finishAfterTransition();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        // make the view visible and start the animation
        rootView.setVisibility(View.VISIBLE);
        circularReveal.start();
    }
}
