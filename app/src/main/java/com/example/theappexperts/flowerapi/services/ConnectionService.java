package com.example.theappexperts.flowerapi.services;

import com.example.theappexperts.flowerapi.API_Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TheAppExperts on 27/09/2017.
 */

    public class ConnectionService {

        static Retrofit retrofit;
        static OkHttpClient okHttpClient;

        public static reqInterface getConnectionService(){

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(reqInterface.class);
        }
}
