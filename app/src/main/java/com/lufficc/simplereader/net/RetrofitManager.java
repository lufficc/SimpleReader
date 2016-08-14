package com.lufficc.simplereader.net;

import com.lufficc.simplereader.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lcc_luffy on 2016/8/8.
 */

public class RetrofitManager {
    private Retrofit retrofit;

    private Api api;

    private static final class Holder {
        private static final RetrofitManager RETROFIT_MANAGER = new RetrofitManager();
    }

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://lufficc.ittun.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Api api() {
        return Holder.RETROFIT_MANAGER.api == null ? Holder.RETROFIT_MANAGER.api = get().create(Api.class) : Holder.RETROFIT_MANAGER.api;
    }

    public static Retrofit get() {
        return Holder.RETROFIT_MANAGER.retrofit;
    }
}
