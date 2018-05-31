package com.parenting.attendance.domain.interactor;

import com.parenting.attendance.domain.models.CryptoList;
import com.parenting.attendance.domain.executor.PostExecutionThread;
import com.parenting.attendance.domain.executor.ThreadExecutor;
import com.parenting.attendance.domain.repository.Cacheble;
import com.parenting.attendance.domain.repository.CryptoListRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by adwait on 29/11/17.
 */

public class CoinListUseCase extends UseCase<CryptoList,Map<String,String>> {

    CryptoListRepository repository;

    @Inject
    public CoinListUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,CryptoListRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Observable<CryptoList> buildUseCaseObservable(Map<String, String> params) {
        try {
            return repository.get(Cacheble.KEY_TEMP,params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
