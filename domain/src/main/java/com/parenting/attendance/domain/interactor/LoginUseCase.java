package com.parenting.attendance.domain.interactor;

import com.parenting.attendance.domain.executor.PostExecutionThread;
import com.parenting.attendance.domain.executor.ThreadExecutor;
import com.parenting.attendance.domain.models.LoginResponse;
import com.parenting.attendance.domain.repository.LoginRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by adwait on 21/03/18.
 */

public class LoginUseCase extends UseCase<LoginResponse,Map<String,String>> {

    private LoginRepository repository;

    @Inject
    LoginUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LoginRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    Observable<LoginResponse> buildUseCaseObservable(Map<String, String> stringStringMap) {
        return null;
    }
}
