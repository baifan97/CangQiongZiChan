package com.whitefan.cangqiongzichan

import android.text.TextUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.whitefan.cangqiongzichan.util.StringCallBack



object HttpUtil {
    /*
   检查ck有效性
   * */
    fun getUserInfoByCk(ck: String, callback: StringCallBack?) {
        if (TextUtils.isEmpty(ck)) return
        OkGo.get<String>("https://wxapp.m.jd.com/kwxhome/myJd/home.json?&useGuideModule=0&bizId=&brandId=&fromType=wxapp&timestamp=" + System.currentTimeMillis())
            .headers("Cookie", ck)
            .headers("content-type", "application/x-www-form-urlencoded")
            .headers("Connection", "keep-alive")
            .headers("Referer", "https://servicewechat.com/wxa5bf5ee667d91626/161/page-frame.html")
            .headers("Host", "wxapp.m.jd.com")
            .headers("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.10(0x18000a2a) NetType/WIFI Language/zh_CN")
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>) {
                    callback?.onSuccess(response.body())
                }

                override fun onError(response: Response<String>) {
                    super.onError(response)
                }
            })
    }
    /*
    日资产查询
    调用Saobing在线资产查询接口，需自行搭建
    * */
    fun getUserInfoLookUp(zcurl: String,ck: String, callback: StringCallBack?) {
        if (TextUtils.isEmpty(ck)) return
        OkGo.post<String>(zcurl)
            .params("cookie", ck)
            .headers("Content-Type", "application/x-www-form-urlencoded")
            .headers("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>) {
                    callback?.onSuccess(response.body())
                }

                override fun onError(response: Response<String>) {
                    super.onError(response)
                }
            })
    }

    /*月资产查询
    * 调用Saobing在线资产查询接口，需自行搭建
    * */
    fun getUserMonthLookUp(mzcurl: String,ck: String, callback: StringCallBack?) {
        if (TextUtils.isEmpty(ck)) return
        OkGo.post<String>(mzcurl)
            .params("cookie", ck)
            .headers("Content-Type", "application/x-www-form-urlencoded")
            .headers("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>) {
                    callback?.onSuccess(response.body())
                }

                override fun onError(response: Response<String>) {
                    super.onError(response)
                }
            })
    }






    public fun cancel(tag: Any) {
        OkGo.getInstance().cancelTag(tag)
    }

    public fun cancelAll() {
        OkGo.getInstance().cancelAll()
    }

}