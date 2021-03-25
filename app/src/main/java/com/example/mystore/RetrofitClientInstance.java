package com.example.mystore;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.neshan.org/v1/";

    public static Retrofit getRetrofitInstance() {

        /*CertificatePinner certPinner = new CertificatePinner.Builder()
                .add("*.neshan.org",
                        "sha256/Cyg7e5STKgZCwdABdPZlqO5lQWSE0KbWr624HoIUuUc=")
                .build();*/

        OkHttpClient client=new OkHttpClient.Builder()
                //.certificatePinner(certPinner)
                .build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}