package com.parenting.attendance.presentation.internal.di.components;

/**
 * Created by adwait on 04/10/17.
 */


import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.internal.di.modules.ActivityModule;
import com.parenting.attendance.presentation.internal.di.modules.CryptoModule;
import com.parenting.attendance.presentation.view.fragment.CryptoListFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CryptoModule.class})
public interface CryptoComponent extends ActivityComponent {
    void inject(CryptoListFragment cryptoListFragment);
}
