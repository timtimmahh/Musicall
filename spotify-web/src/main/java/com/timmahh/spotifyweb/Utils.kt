package com.timmahh.spotifyweb

import android.content.pm.PackageManager
import android.util.Log
import com.google.gson.Gson
import kotlin.reflect.KClass

val gson: Gson = Gson()
fun toString(value: Any?): String {
    if (isNull(value)) return "null value"
    return gson.toJson(value)
}

fun empty(value: CharSequence): Boolean = value.isEmpty()
fun notEmpty(value: CharSequence): Boolean = value.isNotEmpty()
fun notNullEmpty(value: CharSequence?): Boolean = !value.isNullOrEmpty()
fun nullEmpty(value: CharSequence?): Boolean = value.isNullOrEmpty()
fun CharSequence?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()
fun getFirstNotNull(vararg values: Any?): Any {
    if (notNull(values) && values.isNotEmpty()) for (v in values) {
        if (notNull(v)) return v!!
    }; return 0
}

fun isAppInstalled(pkg: String = "", manager: PackageManager): Boolean {
	try {
		manager.getPackageInfo(pkg, 0)
		return true
	} catch (e: PackageManager.NameNotFoundException) {
		return false
	}
}

fun getFirstNotNullEmpty(vararg values: CharSequence?): CharSequence {
    if (notNull(values) && values.isNotEmpty()) for (v in values) {
        if (notNullEmpty(v)) return v!!
    }; return ""
}

fun Any?.NotNull(): Boolean = this != null
fun Any?.Null(): Boolean = this == null
fun notNull(value: Any?): Boolean = value != null
fun isNull(value: Any?): Boolean = value == null
fun Any.getSimpleName(value: KClass<out Any>? = this::class): String = if (notNull(value)) value!!.java.simpleName else "UnknownName"
fun Any.log(msg: String = "No Output", tag: String = getSimpleName()) {
    Log.d(tag, msg)
}

fun Any.logE(msg: String = "No Output", tag: String = getSimpleName()) {
    Log.e(tag, msg)
}