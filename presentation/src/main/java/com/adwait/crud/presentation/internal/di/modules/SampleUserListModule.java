package com.adwait.crud.presentation.internal.di.modules;

import com.adwait.crud.domain.executor.PostExecutionThread;
import com.adwait.crud.domain.executor.ThreadExecutor;
import com.adwait.crud.domain.interactor.AddSampleUserUseCase;
import com.adwait.crud.domain.interactor.SampleUserListUseCase;
import com.adwait.crud.domain.repository.SampleUserListRepository;
import com.adwait.crud.domain.repository.SingleSampleUserRepository;
import com.adwait.crud.presentation.internal.di.PerActivity;
import com.adwait.crud.presentation.mapper.PresentationModelConverter;
import com.adwait.crud.presentation.presenter.SampleUserListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adwait on 22/03/18.
 */
@Module
public class SampleUserListModule {

    public SampleUserListModule() {
    }

    @PerActivity
    @Provides SampleUserListPresenter providesSampleUserListPresenter(PresentationModelConverter converter, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SampleUserListRepository repository, SingleSampleUserRepository singleUserRepository){
        return new SampleUserListPresenter(new SampleUserListUseCase(threadExecutor,postExecutionThread,repository),converter, new AddSampleUserUseCase(threadExecutor,postExecutionThread,singleUserRepository));
    }
}
