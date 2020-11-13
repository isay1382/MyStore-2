package com.example.mystore;

import androidx.paging.DataSource;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourceFactory extends DataSource.Factory{

    private MutableLiveData<PageKeyedDataSource<Integer, Rows>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Rows> create() {

        ItemDataSource itemDataSource = new ItemDataSource();


        itemLiveDataSource.postValue(itemDataSource);

        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Rows>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}