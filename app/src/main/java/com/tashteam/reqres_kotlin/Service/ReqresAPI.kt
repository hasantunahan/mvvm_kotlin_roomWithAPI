package com.tashteam.reqres_kotlin.Service


import com.tashteam.reqres_kotlin.app.Enum.PATH_USER
import com.tashteam.reqres_kotlin.model.Reqres
import com.tashteam.reqres_kotlin.model.ResponseReq
import io.reactivex.Single
import retrofit2.http.GET

interface ReqresAPI {


    @GET(PATH_USER)
    fun getReponseReqres(): Single<ResponseReq<Reqres>>
}