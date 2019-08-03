package com.example.anko

import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

/**
 *
 * <p>
 * 类的描述: 仿照anko 源码, 定义内联函数
 * 创建时间: 2019/7/19 14:19
 * 修改备注:
 */

/**
 * 圆形图片
 */
inline fun ViewManager.roundImageView(theme: Int = 0, init: RoundImageView.() -> Unit): RoundImageView {
    return ankoView({ RoundImageView(it) }, theme = theme, init = init)
}

/**
 * 下边 条纹 bar
 */
inline fun <reified T : View> makeMyLinearItemWithInfo(viewManager: ViewManager // 上级 布局, AnkoCompanent 中使用
                                                       , imageResId: Int = 0 // 头部图片
                                                       , text: String // 头部文字信息
                                                       , linearId: Int = 0 // 整体 LinearLayout 的 id , 预留
                                                       , enterImageRes: Int = R.mipmap.right_forward // 尾部 动作图片
                                                       , myView: T? = null //用户信息显示的view
                                                       , init: T.() -> Unit = {}) = with(viewManager) {
    linearLayout {
        id = linearId
        leftPadding = dip(16)
        rightPadding = dip(16)
        orientation = LinearLayout.HORIZONTAL
        backgroundResource = R.drawable.bottom_side_line_background
        if (imageResId != 0) { //控制是否显示 头部图标
            imageView(imageResId).lparams(width = dip(20), height = dip(20)) {
                gravity = Gravity.CENTER_VERTICAL
            }
        }
        textView(text) {
            // 头部文字显示
            //            textSize = sp(5).toFloat()
            textSize = 14f
            textColorResource = android.R.color.black
            gravity = Gravity.CENTER_VERTICAL or Gravity.START
        }.lparams(width = 0, weight = 1.toFloat(), height = dip(40)) {
            gravity = Gravity.CENTER_VERTICAL
            if (imageResId != 0) leftMargin = dip(16)
        }

        if (myView != null) { //显示用户信息的view
            myView.init()
            myView.lparams(width = wrapContent, height = dip(40)) {
                gravity = Gravity.CENTER_VERTICAL
                rightMargin = dip(16)
            }
            addView(myView)
        }

        if (enterImageRes != 0) { // 尾部 箭头图标
            imageView(enterImageRes).lparams(width = dip(20), height = dip(20)) { gravity = Gravity.CENTER_VERTICAL }.setColorFilter(resources.getColor(android.R.color.black))
        }
    }

}