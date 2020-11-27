package com.example.mystore;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ListProductsShowDeserializer implements JsonDeserializer<ListProductsShow> {
    @Override
    public ListProductsShow deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ListProductsShow listProductsShow=new ListProductsShow();
        Product productShow=new Product();
        Features featuresShow=new Features();
        Brand brand=new Brand();
        StoreShow store=new StoreShow();
        SubCategory subCategory=new SubCategory();
        ViewCountsShow viewCounts=new ViewCountsShow();
        ProductRate productRateShow=new ProductRate();
        JsonObject jsonObject=json.getAsJsonObject();
        final JsonObject product=jsonObject.get("product").getAsJsonObject();
        final JsonObject productsRate=jsonObject.get("productRate").getAsJsonObject();

        int id=product.get("id").getAsInt();
        String name=product.get("name").getAsString();
        String introduction=product.get("introduction").getAsString();
        String description=product.get("description").getAsString();
        JsonObject features=product.get("features").getAsJsonObject();
        int price=product.get("price").getAsInt();
        int discount=product.get("discount").getAsInt();
        int stock=product.get("stock").getAsInt();
        int status=product.get("status").getAsInt();
        String createdAt=product.get("createdAt").getAsString();
        String updatedAt=product.get("updatedAt").getAsString();
        int BrandId=product.get("BrandId").getAsInt();
        int CategoryId=product.get("CategoryId").getAsInt();
        int SubCategoryId=product.get("SubCategoryId").getAsInt();
        int StoreId=product.get("StoreId").getAsInt();
        JsonObject Brand=product.get("Brand").getAsJsonObject();
        JsonObject Store=product.get("Store").getAsJsonObject();
        JsonObject SubCategory=product.get("SubCategory").getAsJsonObject();
        JsonArray ViewCounts=product.get("ViewCounts").getAsJsonArray();

        for (Map.Entry<String, JsonElement> element : features.entrySet()){
            String key = element.getKey();
            JsonObject obj = features.getAsJsonObject(key);
            Map<String, Integer> settingMaps = new HashMap<>();
            for (Map.Entry<String, JsonElement> setting : obj.entrySet()){
                String settingKey = setting.getKey();
                Integer integer = obj.get(settingKey).getAsInt();
                settingMaps.put(settingKey, integer);
            }
            featuresShow.add(key,settingMaps);
        }

        String nameBrand=Brand.get("name").getAsString();
        brand.name=nameBrand;
        String nameStore=Store.get("name").getAsString();
        store.name=nameStore;
        String nameCategory=SubCategory.get("name").getAsString();
        subCategory.name=nameCategory;

        for (JsonElement jsonElementView : ViewCounts){
            JsonObject jsonObjectView=jsonElementView.getAsJsonObject();
            int countView=jsonObjectView.get("count").getAsInt();
            int idView=jsonObjectView.get("id").getAsInt();

            viewCounts.count=countView;
            viewCounts.id=idView;

        }


        productShow.id=id;
        productShow.name=name;
        productShow.introduction=introduction;
        productShow.description=description;
        productShow.featuresShow=featuresShow;
        productShow.price=price;
        productShow.discount=discount;
        productShow.stock=stock;
        productShow.status=status;
        productShow.createdAt=createdAt;
        productShow.updatedAt=updatedAt;
        productShow.BrandId=BrandId;
        productShow.CategoryId=CategoryId;
        productShow.SubCategoryId=SubCategoryId;
        productShow.StoreId=StoreId;
        productShow.brandShow= brand;
        productShow.storeShow=store;
        productShow.subCategoryShow=subCategory;
        productShow.viewCountsShows=viewCounts;


        String sum_of_rates=productsRate.get("sum_of_rates").getAsString();
        String count_of_rates=productsRate.get("count_of_rates").getAsString();

        productRateShow.sum_of_rates=sum_of_rates;
        productRateShow.count_of_rates=count_of_rates;

        listProductsShow.product=productShow;
        listProductsShow.productRate=productRateShow;

        return listProductsShow;
    }
}