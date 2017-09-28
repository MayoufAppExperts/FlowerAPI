package com.example.theappexperts.flowerapi.services;

/**
 * Created by kalpesh on 27/09/2017.
 */

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by kalpesh on 14/08/2017.
 */

public class Offline_CacheData {
    /**
     * Interceptor to cache data and maintain it for a minute.
     * <p>
     * If the same network request is sent within a minute,
     * the response is retrieved from cache.
     */

    public static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }

    /**
     * Interceptor to cache data and maintain it for four weeks.
     * <p>
     * If the device is offline, stale (at most four weeks old)
     * response is fetched from the cache.
     */
    public static class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            final Request[] request = {chain.request()};
            ReactiveNetwork.observeInternetConnectivity()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            request[0] = request[0].newBuilder()
                                    .header("Cache-Control",
                                            "public, only-if-cached, max-stale=" + 2419200)
                                    .build();
                        }
                    });
            return chain.proceed(request[0]);
        }
    }
}