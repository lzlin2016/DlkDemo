package com.example.anko

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 *
 * <p>
 * 类的描述: 自定义布局
 * 创建时间: 2019/7/19 14:39
 * 修改备注:
 */

class MyInputEdit(var imageSizeDip: Int = 20 // 头部图片控件大小
                  , var headImageResource: Int = 0 // 头部图片资源id
                  , var rightChectImageResource: Int = R.mipmap.right_check // 正确检查图片id
                  , var wrongCheckImageResource: Int = R.mipmap.wrong_check // 错误检查图片id
                  , var deleteImageResource: Int = R.mipmap.delete // 删除图片id
                  , var textSizeSp: Float = 14f // 输入框字体的大小
                  , var inputTypeEdit: Int = InputType.TYPE_CLASS_TEXT // 输入框输入的内容样式
                  , var hintResourceInt: Int // 提示文字
                  , var linearBackgroundResource: Int = R.drawable.input_shape_background // 背景资源id
                  , var editImeOptions: Int = EditorInfo.IME_ACTION_NEXT // ime 事件, 默认是 下一步
                  , var editVerifyCondition: (text: String) -> Boolean) { // 输入框 正确错误检查lambda 表达式

    lateinit var inputEdit: LinearLayout
    lateinit var headImage: ImageView
    lateinit var edit: EditText
    lateinit var rightCheck: ImageView
    lateinit var wrongCheck: ImageView
    lateinit var delete: ImageView

    fun getInputText() = edit.text.toString()

    fun showInputCheckIcon(isShowRightCheck: Boolean = false, isShowWrongCheck: Boolean = false, isShowDelete: Boolean = false) {
        rightCheck.visibility = if (isShowRightCheck) View.VISIBLE else View.INVISIBLE
        wrongCheck.visibility = if (isShowWrongCheck) View.VISIBLE else View.INVISIBLE
        delete.visibility = if (isShowDelete) View.VISIBLE else View.INVISIBLE
    }

    fun getInputEdit(viewManager: ViewManager): LinearLayout {
        inputEdit = with(viewManager) {
            linearLayout {
                // 手机号码输入框
                leftPadding = dip(16)
                rightPadding = dip(16)
                orientation = LinearLayout.HORIZONTAL // 默认水平, 不用添加属性
                backgroundResource = linearBackgroundResource
                if (headImageResource != 0) {
                    headImage = imageView(headImageResource)
                            .lparams(width = dip(imageSizeDip), height = dip(imageSizeDip)) {
                                gravity = Gravity.CENTER_VERTICAL
                            }
                }

                edit = editText {
                    textSize = sp(textSizeSp).toFloat()
                    textSize = textSizeSp
                    inputType = inputTypeEdit
                    backgroundResource = android.R.color.transparent
                    hintResource = hintResourceInt
                    gravity = Gravity.CENTER_VERTICAL
                    if (inputTypeEdit == InputType.TYPE_CLASS_TEXT) {
                        singleLine = false
                    }
                    minHeight = dip(45)
                    singleLine = true
                    imeOptions = editImeOptions
                    singleLine = true
                }.lparams(width = 0, height = matchParent, weight = 1.toFloat()) {
                    leftMargin = dip(5)
                    rightMargin = dip(5)
                }
                edit.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        showInputCheckIcon(isShowDelete = true) //获取焦点的时候，显示 删除按钮
                    } else {
                        if (edit.text.toString().isEmpty()) { // 失去焦点的时候，判断是否时空，空的话什么都不显示
                            showInputCheckIcon()
                        } else if (editVerifyCondition(edit.text.toString())) { // 根据输入检查 lambda表达式返回结果，显示对号还是叉子
                            showInputCheckIcon(isShowRightCheck = true)
                        } else {
                            showInputCheckIcon(isShowWrongCheck = true)
                        }
                    }
                }
                edit.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        showInputCheckIcon(isShowDelete = true)
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        showInputCheckIcon(isShowDelete = true) //输入内容变化时，显示删除按钮
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        showInputCheckIcon(isShowDelete = true)
                    }
                })

                frameLayout {
                    rightCheck = imageView(rightChectImageResource) {
                        visibility = View.INVISIBLE
                    }.lparams(width = dip(imageSizeDip), height = dip(imageSizeDip)) { }
                    wrongCheck = imageView(wrongCheckImageResource) {
                        visibility = View.INVISIBLE
                    }.lparams(width = dip(imageSizeDip), height = dip(imageSizeDip)) { }
                    delete = imageView(deleteImageResource) {
                        visibility = View.INVISIBLE
                        onClick {
                            edit.setText("")
                        }
                    }.lparams(width = dip(imageSizeDip), height = dip(imageSizeDip)) { }
                }.lparams(width = wrapContent, height = wrapContent) { gravity = Gravity.CENTER_VERTICAL }
            }
        }
        return inputEdit
    }
}