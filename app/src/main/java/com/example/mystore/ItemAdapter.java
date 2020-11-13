package com.example.mystore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends PagedListAdapter<Rows, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    ItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_item_products, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Rows rows = getItem(position);

        if (rows != null) {
            holder.txtName.setText(rows.name);
            holder.txtNameStore.setText(rows.store.name);
            holder.txtPrice.setText(String.valueOf(rows.price));
            holder.txtDisPrice.setText(String.valueOf(rows.discount));
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),ShowProducts.class);
                    intent.putExtra("id",String.valueOf(rows.id));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });

        }else{
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    private static DiffUtil.ItemCallback<Rows> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Rows>() {
                @Override
                public boolean areItemsTheSame(Rows oldItem, Rows newItem) {
                    return oldItem.id == newItem.id;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Rows oldItem, Rows newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView txtName;
        TextView txtNameStore;
        TextView txtPrice;
        TextView txtDisPrice;
        ImageView imgProducts;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name_Item);
            txtNameStore = itemView.findViewById(R.id.nameStore_Item);
            txtPrice = itemView.findViewById(R.id.price_Item);
            txtDisPrice = itemView.findViewById(R.id.disPrice_Item);
            imgProducts = itemView.findViewById(R.id.img_Item);
            relativeLayout=itemView.findViewById(R.id.relative_Item);
        }
    }
}
