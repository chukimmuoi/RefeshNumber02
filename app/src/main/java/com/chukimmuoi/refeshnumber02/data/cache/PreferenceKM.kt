package com.chukimmuoi.refeshnumber02.data.cache

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author  : Pos365
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : chukimmuoi@gmail.com
 * @Website : https://cafe365.pos365.vn/
 * @Project : RefeshNumber02
 * Created by chukimmuoi on 6/30/19.
 *
 * Shared Preferences as Property Delegates (from the Kotlin for Android Developers book)
 * @author Alexander Gherschon
 */

class PreferenceKM<T>(
    private val context: Context,
    private val name: String,
    private val default: T): ReadWriteProperty<Any?, T> {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("cache_data_setting", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long    -> getLong(name, default)
            is String  -> getString(name, default)
            is Int     -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float   -> getFloat(name, default)
            else       -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
        @Suppress("UNCHECKED_CAST")
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long    -> putLong(name, value)
            is String  -> putString(name, value)
            is Int     -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float   -> putFloat(name, value)
            else       -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }.apply()
    }
}