package com.example.mystore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("products")
    Call<ListProducts> getAnswers(@Query("page") int page, @Query("pageSize") int pageSize);
}
