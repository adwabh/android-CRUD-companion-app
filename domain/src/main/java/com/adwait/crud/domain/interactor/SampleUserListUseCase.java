package com.adwait.crud.domain.interactor;

import com.adwait.crud.domain.executor.PostExecutionThread;
import com.adwait.crud.domain.executor.ThreadExecutor;
import com.adwait.crud.domain.models.SampleUserListResponse;
import com.adwait.crud.domain.repository.Cacheble;
import com.adwait.crud.domain.repository.SampleUserListRepository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by adwait on 21/03/18.
 */
@Singleton
public class SampleUserListUseCase extends UseCase<SampleUserListResponse,Map<String,String>> {

    private final SampleUserListRepository repository;

    @Inject
    public SampleUserListUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, SampleUserListRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Observable<SampleUserListResponse> buildUseCaseObservable(Map<String, String> params) {
            Observable<SampleUserListResponse> repo = null;
        try {
            repo = repository.get(Cacheble.KEY_TEMP, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return repo;
        }
    }
}
