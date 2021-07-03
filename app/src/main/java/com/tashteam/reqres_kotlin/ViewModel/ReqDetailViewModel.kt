package com.tashteam.reqres_kotlin.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tashteam.reqres_kotlin.app.Base.BaseViewModel
import com.tashteam.reqres_kotlin.model.Reqres
import com.tashteam.reqres_kotlin.Room.ReqresDatabase
import kotlinx.coroutines.launch

class ReqDetailViewModel(application: Application) : BaseViewModel(application) {

    var reqDetail = MutableLiveData<Reqres>()
    private val reqDao = ReqresDatabase(getApplication()).reqresDao()


    fun getDetailsData(uuıd: Int) {
        launch {
            reqDetail.value = reqDao.getReqresByUUID(uuıd)
        }
    }


}