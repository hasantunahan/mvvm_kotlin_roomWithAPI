package com.tashteam.reqres_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Reqres(
        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0,
        @ColumnInfo(name = "first_name")
        @SerializedName("first_name")
        val firstName: String?,
        @ColumnInfo(name = "last_name")
        @SerializedName("last_name")
        val lastName: String?,
        @ColumnInfo(name = "email")
        @SerializedName("email")
        val email: String?,
        @ColumnInfo(name = "avatar")
        @SerializedName("avatar")
        val avatar: String?
)
