package com.example.tempdemo.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.tempdemo.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView img;

    private MyViewHolder(View itemView, Context context, int type) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
    }

    public static MyViewHolder create(Context context, int type) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_circle_list, null), context, type);
    }
}
