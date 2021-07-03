package com.tashteam.reqres_kotlin.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tashteam.reqres_kotlin.model.Reqres

@Dao
interface ReqresDao {

    @Insert
    suspend fun insertAllReqres(vararg reqres: Reqres): List<Long>

    @Query("Select * from reqres")
    suspend fun getAllReqres(): List<Reqres>

    @Query("Select * from reqres where uuid =:reqresid")
    suspend fun getReqresByUUID(reqresid: Int): Reqres

    @Query("Delete from reqres")
    suspend fun deleteReqres()

}