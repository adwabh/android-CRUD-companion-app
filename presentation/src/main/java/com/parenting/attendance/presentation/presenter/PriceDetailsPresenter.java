/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parenting.attendance.presentation.presenter;

import android.support.annotation.NonNull;

import com.parenting.attendance.domain.models.Price;
import com.parenting.attendance.domain.exception.DefaultErrorBundle;
import com.parenting.attendance.domain.exception.ErrorBundle;
import com.parenting.attendance.domain.interactor.DefaultObserver;
import com.parenting.attendance.domain.interactor.GetPriceDetails;
import com.parenting.attendance.presentation.exception.ErrorMessageFactory;
import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.mapper.PriceModelDataMapper;
import com.parenting.attendance.presentation.model.PriceModel;
import com.parenting.attendance.presentation.view.PriceDetailsView;

import java.util.Map;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PriceDetailsPresenter implements Presenter {

  private PriceDetailsView viewDetailsView;

  private final GetPriceDetails getPriceDetailsUseCase;
  private final PriceModelDataMapper priceModelDataMapper;

  @Inject
  public PriceDetailsPresenter(GetPriceDetails getPriceDetailsUseCase,
                               PriceModelDataMapper priceModelDataMapper) {
    this.getPriceDetailsUseCase = getPriceDetailsUseCase;
    this.priceModelDataMapper = priceModelDataMapper;
  }

  public void setView(@NonNull PriceDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getPriceDetailsUseCase.dispose();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving user details.
   * @param params
   */
  public void initialize(Map<String, String> params) {
    this.hideViewRetry();
    this.showViewLoading();
    this.getPriceDetails(params);
  }

  private void getPriceDetails(Map<String, String> params) {
    this.getPriceDetailsUseCase.execute(new PriceDetailsObserver(), params);
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showPriceDetailsInView(Price price) {
    final PriceModel priceModel = this.priceModelDataMapper.transform(price);
    this.viewDetailsView.renderPrice(priceModel);
  }

  private final class PriceDetailsObserver extends DefaultObserver<Price> {

    @Override public void onComplete() {
      PriceDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      PriceDetailsPresenter.this.hideViewLoading();
      PriceDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      PriceDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(Price price) {
      PriceDetailsPresenter.this.showPriceDetailsInView(price);
    }
  }
}
