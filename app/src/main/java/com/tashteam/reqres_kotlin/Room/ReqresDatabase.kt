package com.tashteam.reqres_kotlin.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tashteam.reqres_kotlin.model.Reqres

@Database(entities = arrayOf(Reqres::class), version = 1)
abstract class ReqresDatabase : RoomDatabase() {


    abstract fun reqresDao(): ReqresDao


    companion object {

        @Volatile
        private var instance: ReqresDatabase? = null

        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext, ReqresDatabase::class.java, "ReqresDatabase"
        ).build()
    }

}