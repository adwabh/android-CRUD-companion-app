package com.adwait.crud.presentation.internal.di.components;

/**
 * Created by adwait on 04/10/17.
 */


import com.adwait.crud.presentation.internal.di.PerActivity;
import com.adwait.crud.presentation.internal.di.modules.ActivityModule;
import com.adwait.crud.presentation.internal.di.modules.SampleUserListModule;
import com.adwait.crud.presentation.presenter.SampleUserListPresenter;
import com.adwait.crud.presentation.view.activity.SampleUserActivity;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SampleUserListModule.class})
public interface SampleUserListComponent {
    SampleUserListPresenter providesSampleUserListPresenter();
    void present(SampleUserActivity activity);
}
