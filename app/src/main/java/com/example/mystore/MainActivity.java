package com.example.mystore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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