package com.parenting.attendance.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.parenting.attendance.R;
import com.parenting.attendance.presentation.internal.di.components.CoinListComponent;
import com.parenting.attendance.presentation.model.CryptoList;
import com.parenting.attendance.presentation.model.LTC;
import com.parenting.attendance.presentation.presenter.CoinListPresenter;
import com.parenting.attendance.presentation.view.CoinListView;
import com.parenting.attendance.presentation.view.adapter.CoinListAdapter;

import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by adwait on 15/01/18.
 */

public class CoinListFragment extends BaseFragment implements CoinListView {

    @Inject
    CoinListPresenter mCoinListPresenter;

    @Inject
    CoinListAdapter mCoinListAdapter;
    private RecyclerView recyclerView_coin;
    private CoinListListener coinListListener;
    private RelativeLayout relativeLayout_progress;
    private RelativeLayout relativeLayout_retry;



    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CoinListComponent.class).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
//        setupRecyclerView();
        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        this.priceDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
//            this.loadCoinList(params);
        }
    }

    private void loadCoinList(Map<String, String> params) {
        mCoinListPresenter.loadCoinList(params);
    }

    @Override public void onResume() {
        super.onResume();
        this.mCoinListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.mCoinListPresenter.pause();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        recyclerView_coin.setAdapter(null);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mCoinListPresenter.destroy();
    }

    @Override public void onDetach() {
        super.onDetach();
        this.coinListListener = null;
    }

    @Override public void showLoading() {
        this.relativeLayout_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override public void hideLoading() {
        this.relativeLayout_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override public void showRetry() {
        this.relativeLayout_retry.setVisibility(View.VISIBLE);
    }

    @Override public void hideRetry() {
        this.relativeLayout_retry.setVisibility(View.GONE);
    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void renderCoinList(CryptoList price) {
        try {
            if (price!=null&&price.getCoinList()!=null) {
                mCoinListAdapter.setList(price.getCoinList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private interface CoinListListener {
        void onCoinCLicked(LTC model);
    }
}
