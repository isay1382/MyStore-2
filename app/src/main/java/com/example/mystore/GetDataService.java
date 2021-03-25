package com.example.mystore;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.google.gson.internal.$Gson$Types;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GetDataService {

    @Headers("Api-Key: service.i4rdG2OlQ4AngNKIaay9hUBe1DBTOsuFhzRiRfY5")
    @GET
    Call<NeshanAddress> getNeshanAddress(@Url String url);


    @Headers("Api-Key: service.i4rdG2OlQ4AngNKIaay9hUBe1DBTOsuFhzRiRfY5")
    @GET
    Call<MapMatching> getMapMatching(@Url String url);

}
