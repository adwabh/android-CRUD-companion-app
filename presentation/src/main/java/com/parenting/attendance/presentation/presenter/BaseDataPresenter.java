package com.parenting.attendance.presentation.presenter;

/**
 * Created by adwait on 21/03/18.
 */

public abstract class BaseDataPresenter implements Presenter {

    protected abstract void showViewLoading();

    protected abstract void hideViewLoading();

    protected abstract void showViewRetry();

    protected abstract void hideViewRetry();

    protected abstract void showErrorMessage(String message);
}
