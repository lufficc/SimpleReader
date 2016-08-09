package com.lufficc.simplereader.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lcc_luffy on 2016/8/8.
 */

public class RetrofitManager {
    private Retrofit retrofit;

    private static final class Holder {
        private static final RetrofitManager RETROFIT_MANAGER = new RetrofitManager();
    }

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.101:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit get() {
        return Holder.RETROFIT_MANAGER.retrofit;
    }
}
