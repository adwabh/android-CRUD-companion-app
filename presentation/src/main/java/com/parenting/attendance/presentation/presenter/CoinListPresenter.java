package com.parenting.attendance.presentation.presenter;

import com.parenting.attendance.domain.exception.ErrorBundle;
import com.parenting.attendance.domain.interactor.CoinListUseCase;
import com.parenting.attendance.domain.interactor.DefaultObserver;
import com.parenting.attendance.domain.models.CryptoList;
import com.parenting.attendance.presentation.exception.ErrorMessageFactory;
import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.mapper.PresentationModelConverter;
import com.parenting.attendance.presentation.view.CoinListView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by adwait on 29/11/17.
 */

@PerActivity
public class CoinListPresenter implements Presenter {

    private CoinListView coinListView;

    private final CoinListUseCase coinListUseCase;
    private final PresentationModelConverter presentationModelConverter;
    private CoinListObserver mCoinListObserver;

    @Inject
    public CoinListPresenter(CoinListUseCase coinListUseCase,
                                 PresentationModelConverter presentationModelConverter) {
        this.coinListUseCase = coinListUseCase;
        this.presentationModelConverter = presentationModelConverter;
        mCoinListObserver = new CoinListObserver();
    }

    private void showViewLoading() {
        this.coinListView.showLoading();
    }

    private void hideViewLoading() {
        this.coinListView.hideLoading();
    }

    private void showViewRetry() {
        this.coinListView.showRetry();
    }

    private void hideViewRetry() {
        this.coinListView.hideRetry();
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.coinListView.context(),
                errorBundle.getException());
        this.coinListView.showError(errorMessage);
    }

    private void showPriceDetailsInView(CryptoList price) {
        final com.parenting.attendance.presentation.model.CryptoList priceModel;
        try {
            priceModel = this.presentationModelConverter.convert(price);
            this.coinListView.renderCoinList(priceModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void setCoinListView(CoinListView coinListView) {
        this.coinListView = coinListView;
    }

    public void loadCoinList(Map<String, String> params) {
        coinListUseCase.execute(mCoinListObserver,params);
//        showPriceDetailsInView();
    }

    private class CoinListObserver extends DefaultObserver{
        @Override
        public void onNext(Object o) {
            super.onNext(o);
        }

        @Override
        public void onComplete() {
            super.onComplete();
        }

        @Override
        public void onError(Throwable exception) {

        }
    }

}
