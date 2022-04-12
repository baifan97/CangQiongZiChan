package com.whitefan.cangqiongzichan


import com.whitefan.cangqiongzichan.util.SharedPreferencesUtils


object CacheUtil {
    private const val PREFS = "page_cache_config"

    fun getString(key: String?): String? {
        return SharedPreferencesUtils.getPrefs(MyApplication.mInstance, PREFS).getString(key, "")
    }


    fun putString(key: String?, value: String?) {
        SharedPreferencesUtils.getEditor(MyApplication.mInstance, PREFS)
            .putString(key, value).commit()
    }



}