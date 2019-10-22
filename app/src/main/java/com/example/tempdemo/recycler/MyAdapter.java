package com.example.tempdemo.recycler;

import android.content.Context;
import android.view.ViewGroup;

import com.example.tempdemo.R;
import com.example.tempdemo.recycler.base.BaseReclyerViewAdapter;

public class MyAdapter extends BaseReclyerViewAdapter<MyViewHolder, String> {
    private Context mContext;

    public MyAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public MyViewHolder createVH(ViewGroup parent, int viewType) {
        return MyViewHolder.create(mContext, viewType);
    }

    @Override
    public void bindVH(MyViewHolder holder, int position) {
        switch (position % 3) {
            case 0:
                holder.img.setImageResource(R.mipmap.a);
                break;
            case 1:
                holder.img.setImageResource(R.mipmap.b);
                break;
            default:
                holder.img.setImageResource(R.mipmap.c);
                break;

        }

    }
}
