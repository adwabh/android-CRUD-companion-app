package com.parenting.attendance.data.entity;

import java.util.List;

/**
 * Created by adwait on 17/11/17.
 */

public class CryptoList {

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
}
