package com.example.anko

import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = ui.apply {
        verticalLayout {
            padding = dip(16)

            owner.textView = textView(ctx.resources.getText(R.string.strKey)) {
                textColorResource = R.color.colorPrimary
                textSize = 20f
                gravity = Gravity.CENTER
            }.lparams(width = wrapContent, height = wrapContent) {
                margin = dip(3)
            }


            roundImageView {
                imageResource = R.mipmap.sakuraki
            }.lparams(width = wrapContent, height = wrapContent) {
                gravity = Gravity.CENTER_HORIZONTAL
            }


            //由于部分成员变量有定义默认值, 因此, 一般情况下, 只需要如下几个参数
            val inputPhone = MyInputEdit(headImageResource = R.mipmap.phone // 头部图片
                    , hintResourceInt = R.string.abc_search_hint) {// 输入提示文字
                text ->
                text.length == 11 // 输入内容判断拉 lambda 表达式输出, 判断手机号是否为11号
            }
            // 构建输入框 layout , 设置宽高, margin 属性等
            inputPhone.getInputEdit(viewManager = this).lparams(width = matchParent, height = dip(45)) {
                topMargin = dip(8)
            }

            // 密码输入框
            val inputPwd = MyInputEdit(headImageResource = R.mipmap.phone // 头部图片
                    , hintResourceInt = R.string.abc_search_hint // 输入提示文字
                    , editImeOptions = EditorInfo.IME_ACTION_SEND
                    , inputTypeEdit = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) { // 密码方式
                text ->
                text.length >= 8  // 输入内容判断拉 lambda 表达式输出, 判断密码长度不能小于8位
            }
            // 构建输入框 layout , 设置宽高, margin 属性等
            inputPwd.getInputEdit(viewManager = this).lparams(width = matchParent, height = dip(45)) {
                topMargin = dip(8)
            }


            // ，用户头像显示， 用户手机号显示，设置  3个动作条
            val headImage = RoundImageView(ctx) // 用户头像显示的view
            makeMyLinearItemWithInfo(viewManager = this, text = "我的头像", myView = headImage) {
                imageResource = R.mipmap.head // 设置 headImage 属性
            }.lparams(width = matchParent, height = dip(58)) // 设置 LinearLayout 属性

            val phone = TextView(ctx)
            makeMyLinearItemWithInfo(viewManager = this, text = "我的手机号", enterImageRes = 0, myView = phone) {
                // phone TextView 属性
                text = "17712345678".replaceRange(3, 7, "*****")
                gravity = Gravity.CENTER_VERTICAL or Gravity.START
                textColorResource = android.R.color.darker_gray
                textSize = 14f
            }.lparams(width = matchParent, height = dip(58))

            makeMyLinearItemWithInfo<View>(viewManager = this, text = "设置", imageResId = R.mipmap.my_set)
                    .lparams(width = matchParent, height = dip(58)) // 设置linearlayout属性
                    .onClick {
                        //给整体linearlayout添加点击事件
                        toast("跳转我的设置页面")
                    }
        }
    }.view
}