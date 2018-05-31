package com.parenting.attendance.data.net;

import com.parenting.attendance.data.entity.CryptoList;
import com.parenting.attendance.data.entity.PriceEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by adwait on 18/09/17.
 */

interface BitTrackerService {

    @GET("/data/price")
    Call<PriceEntity> getPriceDetailsFromApi(@QueryMap Map<String, String> params);

    @GET("/data/")
    Call<CryptoList> getCurrencyList(@QueryMap Map<String, String> params);
}