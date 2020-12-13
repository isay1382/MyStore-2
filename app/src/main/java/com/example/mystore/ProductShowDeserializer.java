package com.example.mystore;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductShowDeserializer implements JsonDeserializer<ListProductsShow> {
    @Override
    public ListProductsShow deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        ListProductsShow listProductsShow = new ListProductsShow();
        Product productShow = new Product();
        //ProductRate productRateShow=new ProductRate();
        ViewCountsShow viewCountsShow = new ViewCountsShow();
        StoreShow storeShow = new StoreShow();
        Brand brandShow = new Brand();
        SubCategory subCategoryShow = new SubCategory();
        Features featuresShow = new Features();

        JsonObject productds = jsonObject.get("product").getAsJsonObject();
        //JsonObject productRateds=jsonObject.get("productRate").getAsJsonObject();

        int id = productds.get("id").getAsInt();
        String name = productds.get("name").getAsString();
        String introduction = productds.get("introduction").getAsString();
        String description = productds.get("description").getAsString();
        JsonObject featuresds = productds.get("features").getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : featuresds.entrySet()) {
            String keyObject = entry.getKey();
            String valueObject = featuresds.get(keyObject).getAsString();
            featuresShow.add(keyObject,valueObject);
        }

        int price = productds.get("price").getAsInt();
        int discount = productds.get("discount").getAsInt();
        int stock = productds.get("stock").getAsInt();
        int status = productds.get("status").getAsInt();
        String createdAt = productds.get("createdAt").getAsString();
        String updatedAt = productds.get("updatedAt").getAsString();
        int BrandId = productds.get("BrandId").getAsInt();
        int CategoryId = productds.get("CategoryId").getAsInt();
        int SubCategoryId = productds.get("SubCategoryId").getAsInt();
        int StoreId = productds.get("StoreId").getAsInt();
        JsonObject Brandds = productds.get("Brand").getAsJsonObject();
        brandShow.name = Brandds.get("name").getAsString();

        JsonObject Storeds = productds.get("Store").getAsJsonObject();
        storeShow.name = Storeds.get("name").getAsString();

        JsonObject SubCategoryds = productds.get("SubCategory").getAsJsonObject();
        subCategoryShow.name = SubCategoryds.get("name").getAsString();

        List<ViewCountsShow> listView = new ArrayList<>();
        JsonArray ViewCountsds = productds.get("ViewCounts").getAsJsonArray();
        for (JsonElement viewElement : ViewCountsds) {
            JsonObject viewJsonObject = viewElement.getAsJsonObject();
            int idView = viewJsonObject.get("id").getAsInt();
            int countView = viewJsonObject.get("count").getAsInt();
            viewCountsShow.id = idView;
            viewCountsShow.count = countView;
            listView.add(viewCountsShow);
        }

        productShow.id = id;
        productShow.name = name;
        productShow.introduction = introduction;
        productShow.description = description;
        productShow.featuresShow = featuresShow;
        productShow.price = price;
        productShow.discount = discount;
        productShow.stock = stock;
        productShow.status = status;
        productShow.createdAt = createdAt;
        productShow.updatedAt = updatedAt;
        productShow.BrandId = BrandId;
        productShow.CategoryId = CategoryId;
        productShow.SubCategoryId = SubCategoryId;
        productShow.StoreId = StoreId;
        productShow.brandShow = brandShow;
        productShow.storeShow = storeShow;
        productShow.subCategoryShow = subCategoryShow;
        productShow.viewCountsShows = listView;

        // productRateShow.sum_of_rates=productRateds.get("sum_of_rates").getAsInt();
        // productRateShow.count_of_rates=productRateds.get("count_of_rates").getAsInt();

        //listProductsShow.productRate=productRateShow;
        listProductsShow.product = productShow;


        return listProductsShow;
    }
}
