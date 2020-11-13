package com.example.mystore;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer,Rows> {

    public static final int PAGE_SIZE = 5;
    private static final int FIRST_PAGE = 0;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Rows> callback) {
        RetrofitClient.getInstance()
                .getApi().getAnswers(FIRST_PAGE, PAGE_SIZE)
                .enqueue(new Callback<ListProducts>() {
                    @Override
                    public void onResponse(Call<ListProducts> call, Response<ListProducts> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().rows, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListProducts> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Rows> callback) {
        RetrofitClient.getInstance()
                .getApi().getAnswers(params.key, PAGE_SIZE)
                .enqueue(new Callback<ListProducts>() {
                    @Override
                    public void onResponse(Call<ListProducts> call, Response<ListProducts> response) {

                        if (response.body() != null) {
                            Integer adjacentKey = (params.key > 0) ? params.key - 1 : null;
                            callback.onResult(response.body().rows, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListProducts> call, Throwable t) {

                    }
                });
    }
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Rows> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE)
                .enqueue(new Callback<ListProducts>() {
                    @Override
                    public void onResponse(Call<ListProducts> call, Response<ListProducts> response) {

                        if (response.body() != null) {
                            Integer key = (params.key < 0) ? params.key + 1 : null;
                            callback.onResult(response.body().rows, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListProducts> call, Throwable t) {

                    }
                });
    }
}
