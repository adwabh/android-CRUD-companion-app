package com.parenting.attendance.presentation.internal.di.components;

/**
 * Created by adwait on 04/10/17.
 */


import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.internal.di.modules.ActivityModule;
import com.parenting.attendance.presentation.internal.di.modules.LoginModule;
import com.parenting.attendance.presentation.presenter.LoginPresenter;
import com.parenting.attendance.presentation.view.activity.LoginActivity;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginModule.class})
public interface LoginComponent  {
    LoginPresenter providesLoginPresenter();
    void present(LoginActivity activity);
}
