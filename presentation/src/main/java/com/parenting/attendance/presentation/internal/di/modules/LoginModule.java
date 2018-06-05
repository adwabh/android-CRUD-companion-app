package com.parenting.attendance.presentation.internal.di.modules;

import com.parenting.attendance.domain.executor.PostExecutionThread;
import com.parenting.attendance.domain.executor.ThreadExecutor;
import com.parenting.attendance.domain.interactor.LoginUseCase;
import com.parenting.attendance.domain.repository.LoginRepository;
import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.mapper.PresentationModelConverter;
import com.parenting.attendance.presentation.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adwait on 22/03/18.
 */
@Module
public class LoginModule {

    public LoginModule() {
    }

    @PerActivity
    @Provides LoginPresenter providesLoginPresenter(PresentationModelConverter converter, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LoginRepository loginRepository){
        return new LoginPresenter(new LoginUseCase(threadExecutor,postExecutionThread,loginRepository),converter);
    }

}
