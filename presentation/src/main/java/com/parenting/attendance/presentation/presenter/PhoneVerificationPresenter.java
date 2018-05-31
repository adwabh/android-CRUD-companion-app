package com.parenting.attendance.presentation.presenter;

import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.domain.exception.DefaultErrorBundle;
import com.parenting.attendance.domain.exception.ErrorBundle;
import com.parenting.attendance.domain.interactor.DefaultObserver;
import com.parenting.attendance.domain.interactor.GetPhoneVerification;
import com.parenting.attendance.domain.models.VerificationResponse;
import com.parenting.attendance.presentation.exception.ErrorMessageFactory;
import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.mapper.PresentationModelConverter;
import com.parenting.attendance.presentation.view.VerificationView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by adwait on 16/03/18.
 */
@PerActivity
public class PhoneVerificationPresenter implements Presenter {

    private VerificationView verificationView;

    private PresentationModelConverter dataMapper;

    private GetPhoneVerification verificationUseCase;

    @Inject
    public PhoneVerificationPresenter(PresentationModelConverter dataMapper, GetPhoneVerification verificationUseCase){
        this.dataMapper = dataMapper;
        this.verificationUseCase = verificationUseCase;
    }

    private void getVerification(String phoneNo,String password) {
        Map<String,String> params = new HashMap<>();
        params.put(RestApi.PARAMS.MOBILE,phoneNo);
        params.put(RestApi.PARAMS.PASSWORD,password);
        this.verificationUseCase.execute(new PhoneVerificationObserver(), params);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.verificationUseCase.dispose();
        this.verificationView = null;
    }

    private void showViewLoading() {
        this.verificationView.showLoading();
    }

    private void hideViewLoading() {
        this.verificationView.hideLoading();
    }

    private void showViewRetry() {
        this.verificationView.showRetry();
    }

    private void hideViewRetry() {
        this.verificationView.hideRetry();
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving user details.
     */
    public void initialize(String phoneNo, String password) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getVerification(phoneNo,password);
    }

    public void showVarificationCompleteOnView(VerificationResponse response){
        if (verificationView!=null) {
            verificationView.onVerificationSuccess(response);
        }
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.verificationView.context(),
                errorBundle.getException());
        this.verificationView.showError(errorMessage);
    }

    public void setVerificationView(VerificationView verificationView) {
        this.verificationView = verificationView;
    }

    public class PhoneVerificationObserver extends DefaultObserver<VerificationResponse> {

        @Override
        public void onNext(VerificationResponse verificationResponse) {
            showVarificationCompleteOnView(verificationResponse);
        }

        @Override
        public void onComplete() {
            PhoneVerificationPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable exception) {
            PhoneVerificationPresenter.this.hideViewLoading();
            PhoneVerificationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) exception));
            PhoneVerificationPresenter.this.showViewRetry();
        }
    }
}
