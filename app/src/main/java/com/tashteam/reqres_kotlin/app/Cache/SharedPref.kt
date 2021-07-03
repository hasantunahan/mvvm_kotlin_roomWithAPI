package com.tashteam.reqres_kotlin.app.Cache

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.tashteam.reqres_kotlin.app.Constant.REQ_ROOM_SAVE_TIME

class SharedPref {

    companion object {

        private var sharedPref: SharedPreferences? = null

        @Volatile
        private var instance: SharedPref? = null

        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeShared(context).also {
                instance = it
            }
        }

        private fun makeShared(context: Context): SharedPref {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPref()
        }
    }

    fun getTime(): Long? {
        return sharedPref?.getLong(REQ_ROOM_SAVE_TIME, 0)
    }

    fun saveTime(time: Long) {
        sharedPref?.edit(commit = true) {
            putLong(REQ_ROOM_SAVE_TIME, time)
        }
    }
}
