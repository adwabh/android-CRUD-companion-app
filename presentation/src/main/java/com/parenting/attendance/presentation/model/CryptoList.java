package com.parenting.attendance.presentation.model;

import com.parenting.attendance.domain.repository.Cacheble;

import java.util.List;

/**
 * Created by adwait on 20/11/17.
 */

public class CryptoList implements Cacheble {

    private List<LTC> coinList;

    public CryptoList(List<LTC> coinList) {
        this.coinList = coinList;
    }

    public List<LTC> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<LTC> coinList) {
        this.coinList = coinList;
    }

    @Override
    public String key() {
        return KEY_TEMP;
    }
}
