package com.example.factory.net;

import com.example.factory.Factory;
import com.xiaohei.common.Common;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork {
    public static Retrofit getRetrofit(){
         OkHttpClient client = new OkHttpClient.Builder().build();
         Retrofit.Builder builder = new Retrofit.Builder();
         Retrofit retrofit =   builder.baseUrl(Common.Constance.URL)
                 .client(client)
                 .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                 .build();
         return retrofit;

    }
}
