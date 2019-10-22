package com.example.tempdemo.recycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tempdemo.R;
import com.example.tempdemo.recycler.animation.ScaleInAnimation;
import com.example.tempdemo.widgets.Titlebar;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewActivity
 * <p>
 * 类的描述: 测试 RecyclerView 动画
 * 创建时间: 2019/8/5 14:57
 * 修改备注:
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private MyAdapter mAdapter;
    private Titlebar titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initTitleBar();
        initRv();
    }

    private void initTitleBar() {
        titlebar = findViewById(R.id.titlebar);
        titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewActivity.this, "点击了 MENU", Toast.LENGTH_SHORT).show();
            }
        });
        titlebar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewActivity.this, "点击了 标题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRv() {
        mRv = findViewById(R.id.rv);
        mAdapter = new MyAdapter(this);
        mAdapter.openLoadAnimation(new ScaleInAnimation());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(mAdapter);
    }

    public static void luanch(Context context) {
        context.startActivity(new Intent(context, RecyclerViewActivity.class));
    }

    public void refresh(View view) {
        List datas = new ArrayList();
        for (int i = 0; i < 10; i++) {
            datas.add("i = " + i);
        }
        if (mAdapter != null) {
            Log.i("lzzz", "RecyclerViewActivity类 -> refresh -> 执行啦 " + mAdapter.getItemCount());
            mAdapter.reset(datas);
        }
    }

    public void loadmore(View view) {
        List datas = new ArrayList();
        for (int i = 0; i < 10; i++) {
            datas.add("i = " + i);
        }
        if (mAdapter != null) {
            Log.i("lzzz", "RecyclerViewActivity类 -> loadmore -> 执行啦 " + mAdapter.getItemCount());
            mAdapter.addAll(datas);
        }
    }
}
