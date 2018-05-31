package com.parenting.attendance.domain.repository;

import com.parenting.attendance.domain.models.Price;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by adwait on 27/09/17.
 */

public interface CryptoRepository {
    Observable<List<Price>> cryptoPriceEntityList();
    Observable<Price> getPriceDataForCrypto(Map<String, String> params);
}
