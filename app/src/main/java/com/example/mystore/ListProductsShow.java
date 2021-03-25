package com.example.mystore;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public HashMap<String, String> settingMap;

    public Features() {
        settingMap = new HashMap<>();
    }

    public void add(String key, String value) {
        settingMap.put(key, value);
    }
}

//class ProductRate {
// public int sum_of_rates;
//public int count_of_rates;
//}

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
    //public String productRate;
}
