package com.whitefan.cangqiongzichan.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.whitefan.cangqiongzichan.R
import kotlinx.android.synthetic.main.dialog_layout_style.*


class NormalInputUrlDialog(var mActivity: Activity) : Dialog(mActivity!!) {
    private lateinit var input: EditText
    private lateinit var inputRemark: EditText
    private lateinit var ok: Button
    var onOkClickListener: OnOkClickListener? = null

    init {
        initView()
        
    }

    fun initView(): NormalInputUrlDialog {
        setContentView(R.layout.dialog_layout_style)

        input = findViewById(R.id.inputColor)
        inputRemark = findViewById(R.id.inputRemark)
        inputRemark.visibility = View.VISIBLE
        inputRemarkKuang.visibility = View.VISIBLE
        ok = findViewById(R.id.ok)
        ok.text = "保存资产查询接口"
        val divierId = context.resources.getIdentifier("android:id/titleDivider", null, null)
        val divider = findViewById<View>(divierId)
        
        divider?.setBackgroundColor(Color.TRANSPARENT)

        window?.setWindowAnimations(R.style.pop_animation)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(context.resources.displayMetrics.widthPixels * 5 / 6, LinearLayout.LayoutParams.WRAP_CONTENT)
        ok.setOnClickListener {

                onOkClickListener?.ok(input.text.toString(), inputRemark.text.toString())

            dismiss()
        }
        return this
    }

    fun pop() {
        if (!isShowing) {
            try {
                show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun show() {
        try {
            super.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface OnOkClickListener {
        fun ok(ck: String, remark: String)
    }
}