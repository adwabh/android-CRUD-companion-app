package com.parenting.attendance.presentation.internal.di.components;

/**
 * Created by adwait on 04/10/17.
 */


import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.internal.di.modules.ActivityModule;
import com.parenting.attendance.presentation.internal.di.modules.CoinListModule;
import com.parenting.attendance.presentation.view.fragment.CoinListFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CoinListModule.class})
public interface CoinListComponent extends ActivityComponent {
    void inject(CoinListFragment cryptoListFragment);
}
