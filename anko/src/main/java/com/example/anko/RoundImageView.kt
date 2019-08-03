package com.example.anko

import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.Config
import android.graphics.Shader.TileMode
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.TypedValue


/**
 * Created by admin on 2018/1/15.
 */

class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatImageView(context, attrs, defStyle) {
    // ImageView类型
    private var type: Int = 0
    // 获取圆角宽度
    private var mBorderRadius: Int = 0
    // 画笔
    private val mPaint: Paint
    // 半径
    private var mRadius: Int = 0
    // 缩放矩阵
    private val mMatrix: Matrix
    // 渲染器,使用图片填充形状
    private var mBitmapShader: BitmapShader? = null
    // 宽度
    private var mWidth: Int = 0
    // 圆角范围
    private var mRectF: RectF? = null

    init {
        // 初始化画笔等属性
        mMatrix = Matrix()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        // 获取自定义属性值
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyle, 0)
        val count = array.indexCount
        for (i in 0 until count) {
            val attr = array.getIndex(i)
            when (attr) {
                R.styleable.RoundImageView_borderRadius ->
                    // 获取圆角大小
                    mBorderRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT.toFloat(), resources.displayMetrics).toInt())
                R.styleable.RoundImageView_imageType ->
                    // 获取ImageView的类型
                    type = array.getInt(R.styleable.RoundImageView_imageType, TYPE_CIRCLE)
            }
        }
        // Give back a previously retrieved StyledAttributes, for later re-use.
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 如果是圆形，则强制宽高一致，以最小的值为准
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(measuredWidth, measuredHeight)
            mRadius = mWidth / 2
            setMeasuredDimension(mWidth, mWidth)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }
        // 设置渲染器
        setShader()
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRectF!!, mBorderRadius.toFloat(), mBorderRadius.toFloat(), mPaint)
        } else {
            canvas.drawCircle(mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), mPaint)
        }
    }

    private fun setShader() {
        val drawable = drawable ?: return
        val bitmap = drawable2Bitmap(drawable)
        mBitmapShader = BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP)
        var scale = 1.0f
        if (type == TYPE_ROUND) {
            scale = Math.max(width * 1.0f / bitmap.width, height * 1.0f / bitmap.height)
        } else if (type == TYPE_CIRCLE) {
            // 取小值，如果取大值的话，则不能覆盖view
            val bitmapWidth = Math.min(bitmap.width, height)
            scale = mWidth * 1.0f / bitmapWidth
        }
        mMatrix.setScale(scale, scale)
        mBitmapShader!!.setLocalMatrix(mMatrix)
        mPaint.shader = mBitmapShader
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    private fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        // 创建画布
        val bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    /**
     * 对外公布的设置borderRadius方法
     *
     * @param borderRadius
     */
    fun setBorderRadius(borderRadiusPx: Int) {
//        val pxValue = dp2px(borderRadiusDip)
        val pxValue = borderRadiusPx
        if (this.mBorderRadius != pxValue) {
            this.mBorderRadius = pxValue
            // 这时候不需要父布局的onLayout,所以只需要调用onDraw即可
            invalidate()
        }
    }

    /**
     * 对外公布的设置形状的方法
     *
     * @param type
     */
    fun setType(type: Int) {
        if (this.type != type) {
            this.type = type
            if (this.type != TYPE_CIRCLE && this.type != TYPE_ROUND) {
                this.type = TYPE_CIRCLE
            }
            // 这个时候改变形状了，就需要调用父布局的onLayout，那么此view的onMeasure方法也会被调用
            requestLayout()
        }
    }

    /**
     * dp2px
     */
    private fun dp2px(`val`: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, `val`.toFloat(), resources.displayMetrics).toInt()
    }

    companion object {
        // 圆形图片
        val TYPE_CIRCLE = 0
        // 圆角图片
        val TYPE_ROUND = 1
        // 默认圆角宽度
        private val BORDER_RADIUS_DEFAULT = 10
    }
}
