package com.whitefan.cangqiongzichan

import android.animation.ObjectAnimator
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.whitefan.cangqiongzichan.dialog.NewStyleDialog
import com.whitefan.cangqiongzichan.dialog.*
import com.whitefan.cangqiongzichan.util.*
import org.json.JSONObject


class MainActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_main

    }


    override fun initView() {

        Tip.visibility = View.VISIBLE
        lookupdata.visibility = View.GONE
    }

    override fun initData() {

        if (TextUtils.isEmpty(CacheUtil.getString("zcurl")) && TextUtils.isEmpty(CacheUtil.getString("mzcurl")) ) {
            Log.i("====", "没有添加资产查询接口")
            nourladdurl.visibility = View.VISIBLE
            zccxanniu.visibility = View.GONE
            zccxjiekou.visibility = View.GONE
            lookupjieguo.visibility = View.GONE

        } else {
            Log.i("====", "有资产查询接口")
            nourladdurl.visibility = View.GONE
            zccxanniu.visibility = View.VISIBLE
            zccxjiekou.visibility = View.VISIBLE
            lookupjieguo.visibility = View.VISIBLE

            if (TextUtils.isEmpty(CacheUtil.getString("zcurl")) ) {
                Log.i("====", "没有添加日资产查询接口")
                lookupck.visibility = View.GONE
            } else {
                lookupck.setText("日查询")
                lookupck.visibility = View.VISIBLE
                if (TextUtils.isEmpty(CacheUtil.getString("mzcurl")) ) {
                    Log.i("====", "没有添加月资产查询接口")
                    lookupckmonth.visibility = View.GONE
                } else {
                    Log.i("====", "有月资产查询接口")
                    lookupckmonth.visibility = View.VISIBLE
                    lookupckmonth.setText("月查询")
                }
            }
        }
    }

    override fun setEvent() {

        nourladdurl.setOnClickListener {

            val normalInputCKDialog = NormalInputUrlDialog(this,)
            normalInputCKDialog.onOkClickListener = object : NormalInputUrlDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {

                    CacheUtil.putString("mzcurl", ck)//保存月资产接口
                    CacheUtil.putString("zcurl", remark)//保存日资产接口

                    initData()
                    Log.i("====", "1")

                }
            }
            normalInputCKDialog.pop()
        }

        savezcurl.setOnClickListener {

            val normalInputCKDialog = NormalInputUrlDialog(this,)
            normalInputCKDialog.onOkClickListener = object : NormalInputUrlDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {

                    CacheUtil.putString("mzcurl", ck)//保存月资产接口
                    CacheUtil.putString("zcurl", remark)//保存日资产接口

                    initData()
                    Log.i("====", "1")

                }
            }
            normalInputCKDialog.pop()
        }


        dian.setOnClickListener {
            createDialog("活动攻略:",
                "【极速金币】京东极速版->我的->金币(极速版使用)\n" +
                        "【京东赚赚】微信->京东赚赚小程序->底部赚好礼->提现无门槛红包(京东使用)\n" +
                        "【京东秒杀】京东->中间频道往右划找到京东秒杀->中间点立即签到->兑换无门槛红包(京东使用)\n" +
                        "【东东萌宠】京东->我的->东东萌宠,完成是京东红包,可以用于京东app的任意商品\n" +
                        "【领现金】京东->我的->东东萌宠->领现金(微信提现+京东红包)\n" +
                        "【东东农场】京东->我的->东东农场,完成是京东红包,可以用于京东app的任意商品\n" +
                        "【京喜工厂】京喜->我的->京喜工厂,完成是商品红包,用于购买指定商品(不兑换会过期)\n" +
                        "【京东金融】京东金融app->我的->养猪猪,完成是白条支付券,支付方式选白条支付时立减.\n" +
                        "【其他】京喜红包只能在京喜使用,其他同理", "关闭", object : NewStyleDialog.OnRightClickListener {
                    override fun rightClick() {
                        disMissDialog()
                    }
                })
        }


        lookupck.setOnClickListener {

            if (TextUtils.isEmpty(input.text.toString())) {
                Toast.makeText(MyApplication.mInstance, "CK为空", Toast.LENGTH_SHORT).show()
            } else {
                Tip.visibility = View.VISIBLE
                lookupdata.visibility = View.VISIBLE
                lookupdata.setText("正在检测CK有效性...")
                lookupck.setText("正在查询")
                getUserInfo()//检查ck有效性，本地接口
            }
        }

        lookupckmonth.setOnClickListener {

            if (TextUtils.isEmpty(input.text.toString())) {
                Toast.makeText(MyApplication.mInstance, "CK为空", Toast.LENGTH_SHORT).show()
            } else {
                Tip.visibility = View.VISIBLE
                lookupdata.visibility = View.VISIBLE
                lookupdata.setText("正在检测CK有效性...")
                lookupckmonth.setText("正在查询")
                getUserMonthLookUp()//月资产查询，服务器接口，需自行配置
            }
        }


        hideCK.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                inputkuang.visibility = View.GONE
            } else {
                inputkuang.visibility = View.VISIBLE
            }

        }


    }

    private fun getUserInfo() {
        var ck = input.text.toString()
        Toast.makeText(MyApplication.mInstance, "正在检测CK有效性...", Toast.LENGTH_SHORT).show()
        HttpUtil.getUserInfoByCk(ck, object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    val job = JSONObject(result)
                    var name = job.optJSONObject("user").optString("petName")
                    if (!TextUtils.isEmpty(name)) {
                        lookupdata.setText("正在检测CK有效性...\nCookie有效")
//                        getUserIn()
                        getUserInfoLookUp()
                    } else {
                        Toast.makeText(MyApplication.mInstance, "CK有效性检测未通过，查询失败...", Toast.LENGTH_SHORT).show()

                        lookupdata.setText("正在检测CK有效性...\nCookie无效，请更换有效Cookie！")
                        lookupck.setText("日查询")
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Toast.makeText(MyApplication.mInstance, "CK有效性检测未通过，查询失败...", Toast.LENGTH_SHORT).show()
                    lookupdata.setText("正在检测CK有效性...\nCookie失效，请更换有效Cookie")
                    lookupck.setText("日查询")
                }
            }
            override fun onFail() {
                Toast.makeText(MyApplication.mInstance, "连接失败...", Toast.LENGTH_SHORT).show()
                lookupdata.setText("正在检测CK有效性...\n连接失败...")
                lookupck.setText("日查询")
            }
        })
    }


    private fun getUserInfoLookUp() {
        var zcurl = CacheUtil.getString("zcurl").toString()
        var ck = input.text.toString()
        lookupdata.setText("正在检测CK有效性...\nCookie有效\n正在查询中，请不要离开此页面...")
        HttpUtil.getUserInfoLookUp(zcurl,ck, object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    val job = JSONObject(result)

                    var data = job.optString("data")
                    //透明动画
                    var objectAnimator = ObjectAnimator.ofFloat(lookupdata,"alpha",0f,0.6f,1f)
                    objectAnimator.duration = 1000
                    objectAnimator.start()
                    lookupdata.setText(data)
                    Tip.visibility = View.GONE
                    lookupck.setText("日查询")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {

            }
        })
    }

    private fun getUserMonthLookUp() {
        var mzcurl = CacheUtil.getString("mzcurl").toString()
        var ck = input.text.toString()
        lookupdata.setText("正在查询中，请不要离开此页面...")
        HttpUtil.getUserMonthLookUp(mzcurl, ck, object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    val job = JSONObject(result)

                    var data = job.optString("data")
                    //透明动画
                    var objectAnimator = ObjectAnimator.ofFloat(lookupdata,"alpha",0f,0.6f,1f)
                    objectAnimator.duration = 1000
                    objectAnimator.start()
                    lookupdata.setText(data)
                    Tip.visibility = View.GONE
                    lookupckmonth.setText("月查询")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {

            }
        })
    }











}