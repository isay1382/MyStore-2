package com.example.mystore;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientShowProducts {
    public static Retrofit retrofit=null;

    public static Retrofit getShowRetrofitClient(String url){
        if (retrofit==null){
            GsonBuilder gsonBuilder=new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ListProductsShow.class,new ProductShowDeserializer());
            Gson gson=gsonBuilder.create();
            retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
