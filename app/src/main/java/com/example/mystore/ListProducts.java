package com.example.mystore;

import java.util.List;

class Viewcounts{
    public int count;
}

class Storee{
    public String name;
}

class Rows {
    public Storee Store;
    public List<Viewcounts> ViewCounts;
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
