/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.parenting.attendance.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.parenting.attendance.R;
import com.parenting.attendance.presentation.internal.di.HasComponent;
import com.parenting.attendance.presentation.internal.di.components.CryptoComponent;
import com.parenting.attendance.presentation.internal.di.components.DaggerCryptoComponent;
import com.parenting.attendance.presentation.view.fragment.CryptoListFragment;

/**
 * Activity that shows details of a certain user.
 */
public class PriceDetailsActivity extends BaseActivity implements HasComponent<CryptoComponent> {

  private static final String INTENT_EXTRA_PARAM_USER_ID = "org.android10.INTENT_PARAM_USER_ID";
  private static final String INSTANCE_STATE_PARAM_USER_ID = "org.android10.STATE_PARAM_USER_ID";

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, PriceDetailsActivity.class);
//    callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
    return callingIntent;
  }

  private int userId;
  private CryptoComponent cryptoComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeActivity(savedInstanceState);
    this.initializeInjector();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putInt(INSTANCE_STATE_PARAM_USER_ID, this.userId);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
      addFragment(R.id.fragmentContainer, new CryptoListFragment());
    } else {
      this.userId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
    }
  }

  private void initializeInjector() {
    this.cryptoComponent = DaggerCryptoComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public CryptoComponent getComponent() {
    return cryptoComponent;
  }
}
