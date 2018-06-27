package com.adwait.crud.presentation.presenter;

import com.adwait.crud.domain.interactor.AddSampleUserUseCase;
import com.adwait.crud.domain.interactor.DefaultObserver;
import com.adwait.crud.domain.interactor.SampleUserListUseCase;
import com.adwait.crud.domain.models.SampleUserListResponse;
import com.adwait.crud.presentation.internal.di.PerActivity;
import com.adwait.crud.presentation.mapper.PresentationModelConverter;
import com.adwait.crud.presentation.model.SampleUserList;
import com.adwait.crud.presentation.view.SampleUserListView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by adwait on 21/03/18.
 */

@PerActivity
public class SampleUserListPresenter extends BaseDataPresenter implements Presenter{

    private AddSampleUserUseCase addSampleUserUseCase;
    private SampleUserListView sampleUserListView;
    private SampleUserListUseCase useCase;
    private PresentationModelConverter converter;

    @Inject
    public SampleUserListPresenter(SampleUserListUseCase useCase, PresentationModelConverter converter, AddSampleUserUseCase addSampleUserUseCase) {
        this.useCase = useCase;
        this.addSampleUserUseCase = addSampleUserUseCase;
        this.converter = converter;
    }

    public void getSampleUserList(){
        this.hideViewRetry();
        this.showViewLoading();
        Map<String, String> params = new HashMap<>();
//        params.put(RestApi.PARAMS.MOBILE,phoneNo);
//        params.put(RestApi.PARAMS.PASSWORD,password);
        useCase.execute(new SampleUserListObserver(),params);
    }

    public void addUser(SampleUserList.SampleUser user){
        this.hideViewRetry();
        this.showViewLoading();
        addSampleUserUseCase.execute(new AddUserActionObserver(), converter.reverseConvert(user));
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
        sampleUserListView = null;
    }

    public void setSampleUserListView(SampleUserListView sampleUserListView) {
        this.sampleUserListView = sampleUserListView;
    }

    @Override
    protected void showViewLoading() {
        if (sampleUserListView !=null) {
            sampleUserListView.showLoading();
        }
    }

    @Override
    protected void hideViewLoading() {
        if (sampleUserListView !=null) {
            sampleUserListView.hideLoading();
        }
    }

    @Override
    protected void showViewRetry() {
        if (sampleUserListView !=null) {
            sampleUserListView.showRetry();
        }
    }

    @Override
    protected void hideViewRetry() {
        if (sampleUserListView !=null) {
            sampleUserListView.hideRetry();
        }
    }

    @Override
    protected void showErrorMessage(String message) {
        if (sampleUserListView !=null) {
            sampleUserListView.showError(message);
        }
    }

    public class AddUserActionObserver extends DefaultObserver<Boolean>{
        @Override
        public void onNext(Boolean aBoolean) {
            super.onNext(aBoolean);
            String message = aBoolean?"User added successfully":"Failed to add user";
            SampleUserListPresenter.this.showErrorMessage(message);

        }

        @Override
        public void onComplete() {
            SampleUserListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable exception) {
            SampleUserListPresenter.this.hideViewLoading();
            SampleUserListPresenter.this.showErrorMessage(exception.getMessage());
            SampleUserListPresenter.this.showViewRetry();
        }
    }


    public class SampleUserListObserver extends DefaultObserver<SampleUserListResponse>{
        @Override
        public void onNext(SampleUserListResponse sampleUserListResponse) {
            SampleUserListPresenter.this.showSampleUserListOnView(sampleUserListResponse);
        }

        @Override
        public void onComplete() {
            SampleUserListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable exception) {
                SampleUserListPresenter.this.hideViewLoading();
                SampleUserListPresenter.this.showErrorMessage(exception.getMessage());
                SampleUserListPresenter.this.showViewRetry();
        }
    }

    private void showSampleUserListOnView(SampleUserListResponse sampleUserListResponse) {
        SampleUserList sampleUserList = converter.convert(sampleUserListResponse);
        if (sampleUserListView !=null) {
            sampleUserListView.onSampleUserListFetched(sampleUserList);
        }
    }

}
