package com.example.tempdemo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tempdemo.R;

/**
 * 　  Ⅰ、title——对应标题的文字
 * <p>
 * 　　Ⅱ、titleTextSize——对应标题的文字大小
 * <p>
 * 　　Ⅲ、titleTextColor——对应标题的文本颜色
 * <p>
 * 　　Ⅳ、titleLeftText——对应左边按钮的文本
 * <p>
 * 　　Ⅴ、titleLeftBackground——对应左边按钮的背景
 * <p>
 * 　　Ⅵ、titleLeftTextColor——对应左边按钮的文字颜色
 * <p>
 * 　　Ⅶ、titleRightText——对应右边按钮的文本
 * <p>
 * 　　Ⅴ、titleRightBackground——对应右边按钮的背景
 * <p>
 * 　　Ⅵ、titleRightTextColor——对应右边按钮的文字颜色
 */
public class Titlebar extends RelativeLayout {

    private Context context;
    private String title;
    private float titleTextSize;
    private int titleTextColor;
    private String titleLeftText;
    private Drawable titleLeftBackground;
    private int titleLeftTextColor;
    private String titleRightText;
    private Drawable titleRightBackground;
    private int titleRightTextColor;
    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvTitle;
    private int itemHeight;
    private int titleGravity;

    public Titlebar(Context context) {
        this(context, null, 0);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this);

        this.context = context;

        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = this.getContext().obtainStyledAttributes(attrs, R.styleable.titleBar);
        if (ta != null) {
            title = ta.getString(R.styleable.titleBar_title);
            titleTextSize = ta.getDimension(R.styleable.titleBar_titleTextSize, 16);
            titleTextColor = ta.getColor(R.styleable.titleBar_titleTextColor, getResources().getColor(R.color.black33));
            titleLeftText = ta.getString(R.styleable.titleBar_titleLeftText);
            titleLeftBackground = ta.getDrawable(R.styleable.titleBar_titleLeftBackground);
            titleLeftTextColor = ta.getColor(R.styleable.titleBar_titleLeftTextColor, getResources().getColor(R.color.black66));
            titleRightText = ta.getString(R.styleable.titleBar_titleRightText);
            titleRightBackground = ta.getDrawable(R.styleable.titleBar_titleRightBackground);
            titleRightTextColor = ta.getColor(R.styleable.titleBar_titleRightTextColor, getResources().getColor(R.color.black66));
            itemHeight = (int) ta.getDimension(R.styleable.titleBar_itemHeight, context.getResources().getDimension(R.dimen.item_height));
            titleGravity = ta.getInt(R.styleable.titleBar_titleGravity, 1);
            ta.recycle();
        }
    }

    private void initView() {
        // 子控件绑定
//        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this, true);

        tvLeft = findViewById(R.id.tv_left);
        tvRight = findViewById(R.id.tv_right);
        tvTitle = findViewById(R.id.tv_title);

        // 初始化各个控件的属性
        tvLeft.setText(titleLeftText);
        tvLeft.setTextColor(titleLeftTextColor);
        tvLeft.setBackgroundDrawable(titleLeftBackground);

        tvRight.setText(titleRightText);
        tvRight.setTextColor(titleRightTextColor);
        tvRight.setBackgroundDrawable(titleRightBackground);

        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setBackgroundDrawable(titleRightBackground);
        tvTitle.setGravity(titleGravity);

        Log.e("Titlebar", "initView 执行啦");

//        //item整体高度的自定义
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
//        view.setLayoutParams(params);
    }

    public void setLeftClickListener(OnClickListener listener) {
        tvLeft.setOnClickListener(listener);
    }

    public void setRightClickListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    public void setTitleClickListener(OnClickListener listener) {
        tvTitle.setOnClickListener(listener);
    }
}
