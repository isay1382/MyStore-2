package com.example.mystore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ViewCounts{
    public int count;
}

class Store{
    public String name;
}

class Rows {
    @SerializedName("Store")
    public Store store;
    @SerializedName("ViewCounts")
    public List<ViewCounts> viewCounts;
    public int id;
    public String name;
    public String introduction;
    public String description;
    public int price;
    public int discount;
    public int stock;
    public int status;
    public String createdAt;
    public String updatedAt;
    public int BrandId;
    public int CategoryId;
    public int SubCategoryId;
    public int StoreId;
}

public class ListProducts {
    public int count;
    public List<Rows> rows;
}
