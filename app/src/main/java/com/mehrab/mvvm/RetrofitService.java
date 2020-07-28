package com.mehrab.mvvm;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RetrofitDao getInterface() {
        return retrofit.create(RetrofitDao.class);
    }
}
