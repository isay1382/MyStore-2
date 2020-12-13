package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProducts extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps;
    String url="http://karjar.herokuapp.com";

    TextView txtName;
    TextView txtNameStore;
    TextView txtNameSubCategory;
    TextView txtStock;
    TextView txtBrand;
    TextView txtDescription;
    //TextView txtFeatures;
    TextView txtPrice;
    TextView txtDisPrice;
    TextView txtPriceEnd;
    LinearLayout linearAddFeature;

    InterfaceShowProducts interfaceShowProducts;
    LinearLayout.LayoutParams layoutParams;
    //Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        Hash_file_maps=new HashMap<>();

        sliderLayout=findViewById(R.id.slider);

        txtName=findViewById(R.id.name_ShowProducts);
        txtNameStore=findViewById(R.id.nameStore_ShowProducts);
        txtNameSubCategory=findViewById(R.id.nameDasteBandi_ShowProducts);
        txtStock=findViewById(R.id.stock_ShowProducts);
        txtBrand=findViewById(R.id.brand_ShowProducts);
        txtDescription=findViewById(R.id.description_ShowProducts);
        //txtFeatures=findViewById(R.id.features_ShowProducts);
        linearAddFeature=findViewById(R.id.linearAddFeaturesShow);
        txtPrice=findViewById(R.id.price_ShowProducts);
        txtDisPrice=findViewById(R.id.disPrice_ShowProducts);
        txtPriceEnd=findViewById(R.id.priceNahayi_ShowProducts);

        String id=getIntent().getExtras().getString("id");

        Hash_file_maps.put("Android CupCake", "http://androidblog.esy.es/images/cupcake-1.png");
        Hash_file_maps.put("Android Donut", "http://androidblog.esy.es/images/donut-2.png");
        Hash_file_maps.put("Android Eclair", "http://androidblog.esy.es/images/eclair-3.png");
        Hash_file_maps.put("Android Froyo", "http://androidblog.esy.es/images/froyo-4.png");
        Hash_file_maps.put("Android GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");

        for (String name:Hash_file_maps.keySet()){
            TextSliderView textSliderView=new TextSliderView(ShowProducts.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

        interfaceShowProducts=RetrofitClientShowProducts.getShowRetrofitClient(url).create(InterfaceShowProducts.class);
        interfaceShowProducts.getShowProducts(id).enqueue(new Callback<ListProductsShow>() {
            @Override
            public void onResponse(Call<ListProductsShow> call, Response<ListProductsShow> response) {
                if (response.isSuccessful()){
                    ResponseShowProduct(response.body());
                }
            }

            @Override
            public void onFailure(Call<ListProductsShow> call, Throwable t) {
                Toast.makeText(ShowProducts.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ResponseShowProduct(ListProductsShow listProductsShow){
        txtName.setText(listProductsShow.product.name);
        txtNameStore.setText(listProductsShow.product.storeShow.name);
        txtNameSubCategory.setText(listProductsShow.product.subCategoryShow.name);
        txtStock.setText(String.valueOf(listProductsShow.product.stock));
        txtBrand.setText(listProductsShow.product.brandShow.name);
        txtDescription.setText(listProductsShow.product.description);
        for (Map.Entry<String,String> getObject:listProductsShow.product.featuresShow.settingMap.entrySet()){
            String key=getObject.getKey();
            String value=getObject.getValue();
            CustomGetObjectFeatuere customGetObjectFeatuere=new CustomGetObjectFeatuere(ShowProducts.this);
            customGetObjectFeatuere.txtKeyCustom.setText(key+" :");
            customGetObjectFeatuere.txtValueCustom.setText(value);
            layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            linearAddFeature.addView(customGetObjectFeatuere);
        }
        txtPrice.setText(String.valueOf(listProductsShow.product.price));
        txtDisPrice.setText(String.valueOf(listProductsShow.product.discount));

    }

    protected void onStop(){
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}