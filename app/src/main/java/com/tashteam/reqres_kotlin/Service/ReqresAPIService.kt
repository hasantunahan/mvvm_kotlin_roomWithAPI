package com.tashteam.reqres_kotlin.Service

import com.tashteam.reqres_kotlin.app.Constant.BASE_URL
import com.tashteam.reqres_kotlin.model.Reqres
import com.tashteam.reqres_kotlin.model.ResponseReq
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ReqresAPIService {

    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ReqresAPI::class.java)

    fun getReqData(): Single<ResponseReq<Reqres>> {
        return api.getReponseReqres()
    }

}