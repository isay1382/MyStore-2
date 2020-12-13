package com.example.mystore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CustomGetObjectFeatuere extends RelativeLayout {

    public TextView txtKeyCustom;
    public TextView txtValueCustom;

    public CustomGetObjectFeatuere(Context context) {
        super(context);
        init(context);
    }

    public CustomGetObjectFeatuere(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomGetObjectFeatuere(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_get_object_feature,this,true);

        txtKeyCustom=view.findViewById(R.id.txt_custom_Features_key);
        txtValueCustom=view.findViewById(R.id.txt_custom_Features_value);

    }
}
