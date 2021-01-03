package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button buttonMyPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMyPlace =findViewById(R.id.btnMyPlace_ActivityMain);
        buttonMyPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NeshanMap.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.recycleView_GetProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);


        final ItemAdapter adapter = new ItemAdapter(this);



        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Rows>>() {
            @Override
            public void onChanged(PagedList<Rows> rows) {
                adapter.submitList(rows);
            }
        });



        recyclerView.setAdapter(adapter);
    }

    }