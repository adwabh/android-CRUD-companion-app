package com.parenting.attendance.presentation.presenter;

import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.domain.interactor.DefaultObserver;
import com.parenting.attendance.domain.interactor.LoginUseCase;
import com.parenting.attendance.domain.models.LoginResponse;
import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.mapper.PresentationModelConverter;
import com.parenting.attendance.presentation.model.Login;
import com.parenting.attendance.presentation.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by adwait on 21/03/18.
 */

@PerActivity
public class LoginPresenter extends BaseDataPresenter implements Presenter{

    private LoginView loginView;
    private LoginUseCase useCase;
    private PresentationModelConverter converter;

    @Inject
    public LoginPresenter(LoginUseCase useCase, PresentationModelConverter converter) {
        this.useCase = useCase;
        this.converter = converter;
    }

    public void doLogin(String phoneNo, String password){
        this.hideViewRetry();
        this.showViewLoading();
        Map<String, String> params = new HashMap<>();
        params.put(RestApi.PARAMS.MOBILE,phoneNo);
        params.put(RestApi.PARAMS.PASSWORD,password);
        useCase.execute(new LoginObserver(),params);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        useCase.dispose();
        loginView = null;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    protected void showViewLoading() {
        if (loginView!=null) {
            loginView.showLoading();
        }
    }

    @Override
    protected void hideViewLoading() {
        if (loginView!=null) {
            loginView.hideLoading();
        }
    }

    @Override
    protected void showViewRetry() {
        if (loginView!=null) {
            loginView.showRetry();
        }
    }

    @Override
    protected void hideViewRetry() {
        if (loginView!=null) {
            loginView.hideRetry();
        }
    }

    @Override
    protected void showErrorMessage(String message) {
        if (loginView!=null) {
            loginView.showError(message);
        }
    }


    public class LoginObserver extends DefaultObserver<LoginResponse>{
        @Override
        public void onNext(LoginResponse loginResponse) {
            LoginPresenter.this.showLoginDoneOnView(loginResponse);
        }

        @Override
        public void onComplete() {
            LoginPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable exception) {
                LoginPresenter.this.hideViewLoading();
                LoginPresenter.this.showErrorMessage(exception.getMessage());
                LoginPresenter.this.showViewRetry();
        }
    }

    private void showLoginDoneOnView(LoginResponse loginResponse) {
        Login login = converter.convert(loginResponse);
        if (loginView!=null) {
            loginView.onLoginDone(login);
        }
    }

}
