package com.example.mystore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceShowProducts {

    @GET("/products/{id}")
    Call<ListProductsShow> getShowProducts(@Path("id") String id);
}
