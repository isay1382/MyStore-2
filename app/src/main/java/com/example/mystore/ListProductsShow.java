package com.example.mystore;

import com.google.gson.annotations.SerializedName;
import java.util.List;

class ViewCountsShow {
    public int count;
    public int id;
}

class SubCategory {
    public String name;
}

class StoreShow {
    public String name;
}

class Brand {
    public String name;
}

class Features {
    public int count;
    public String color;
}

class Product {
    @SerializedName("Brand")
    public Brand brandShow;
    @SerializedName("Store")
    public StoreShow storeShow;
    @SerializedName("SubCategory")
    public SubCategory subCategoryShow;
    @SerializedName("features")
    public Features featuresShow;
    @SerializedName("ViewCounts")
    public List<ViewCountsShow> viewCountsShows;
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

public class ListProductsShow {
    public Product product;
    public String productRate;
}
