package com.parenting.attendance.data.net;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.parenting.attendance.data.entity.CryptoList;
import com.parenting.attendance.data.entity.deserializer.ListDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by adwait on 17/01/17.
 */

public class RestClient {
    /**
     * This is our main backend/server URL.
     */
//    https://api.klook.com/v1/home?lng=23.4241&lat=53.8478
//    public static final String REST_API_URL = "https://mycosts-app.herokuapp.com/";
    public static final String REST_API_URL = "https://min-api.cryptocompare.com/";

//    public static final String REST_API_URL = "http://192.168.0.12:3000";

/* <p/>
 * This is the main entry point for network communication. Use this class for instancing REST services which do the
 * actual communication.
 */


    /**
     * This is our main backend/server URL.
     */
//    public static final String REST_API_URL = "https://mycosts-app.herokuapp.com/";


    private static Retrofit s_retrofit;

    static {
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(CryptoList.class, new ListDeserializer())
                .create();

        // enable logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        s_retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return s_retrofit.create(serviceClass);
    }
}
