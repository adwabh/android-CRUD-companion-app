package com.adwait.crud.data.net;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.adwait.crud.data.net.RestApi.API_BASE_URL;

/**
 *
 * Created by adwait on 17/01/17.
 */

public class RestClient {

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
                .create();

        // enable logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        s_retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return s_retrofit.create(serviceClass);
    }
}
