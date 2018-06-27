package com.adwait.crud.domain.interactor;

import com.adwait.crud.domain.Constant;
import com.adwait.crud.domain.executor.PostExecutionThread;
import com.adwait.crud.domain.executor.ThreadExecutor;
import com.adwait.crud.domain.models.SampleUserListResponse;
import com.adwait.crud.domain.repository.SingleSampleUserRepository;

import java.util.LinkedHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by adwait on 21/03/18.
 */
@Singleton
public class AddSampleUserUseCase extends UseCase<Boolean,SampleUserListResponse.SampleUser> {

    private final SingleSampleUserRepository repository;

    @Inject
    public AddSampleUserUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SingleSampleUserRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(SampleUserListResponse.SampleUser sampleUser) {
            Observable<Boolean> repo = null;
        try {
            LinkedHashMap<String,String> params = new LinkedHashMap<>();
            params.put(Constant.USERNAME, sampleUser.getUsername());
            params.put(Constant.EMAIL, sampleUser.getEmail());
            repo = repository.insert(sampleUser,params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return repo;
        }
    }
}
