package com.example.mystore;

import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Map<String, Integer>> settingsMap;

    public  Features(){
        settingsMap = new HashMap<>();
    }
    public void add(String key, Map<String, Integer> settingMap){
        settingsMap.put(key, settingMap);
    }

}

class ProductRate {
    public String sum_of_rates;
    public String count_of_rates;
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
    public ViewCountsShow viewCountsShows;
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
    public ProductRate productRate;
}
