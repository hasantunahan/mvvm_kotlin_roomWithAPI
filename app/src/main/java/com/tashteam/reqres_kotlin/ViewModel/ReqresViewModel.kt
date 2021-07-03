package com.tashteam.reqres_kotlin.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tashteam.reqres_kotlin.app.Base.BaseViewModel
import com.tashteam.reqres_kotlin.app.Cache.SharedPref
import com.tashteam.reqres_kotlin.model.Reqres
import com.tashteam.reqres_kotlin.model.ResponseReq
import com.tashteam.reqres_kotlin.Room.ReqresDatabase
import com.tashteam.reqres_kotlin.Service.ReqresAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ReqresViewModel(application: Application) : BaseViewModel(application) {

    private val reqresAPIService = ReqresAPIService()
    private val disposable = CompositeDisposable()
    var reqList = MutableLiveData<List<Reqres>>()
    var errorMsg = MutableLiveData<Boolean>()
    var isLoadingReq = MutableLiveData<Boolean>()
    private val sharedPreferences = SharedPref(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    private val reqDao = ReqresDatabase(getApplication()).reqresDao()

    fun refreshData() {
        val updateTime = sharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLLite()
        } else {
            getDataFromApi()
        }
    }

    fun refreshAPI(){
        getDataFromApi()
    }

    fun getDataFromSQLLite() {
        launch {
            showData(reqDao.getAllReqres())
            Toast.makeText(getApplication(), "SQL lite listening", Toast.LENGTH_LONG).show()
        }
    }

    fun getDataFromApi() {
        isLoadingReq.value = true
        disposable.add(
                reqresAPIService.getReqData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<ResponseReq<Reqres>>() {
                            override fun onSuccess(t: ResponseReq<Reqres>) {
                                storeInSQLlite(t.data)
                            }

                            override fun onError(e: Throwable) {
                                errorMsg.value = true
                                isLoadingReq.value = false
                                e.printStackTrace()
                            }
                        })
        )
        Toast.makeText(getApplication(), "API listening", Toast.LENGTH_LONG).show()


    }


    fun showData(list: List<Reqres>) {
        reqList.value = list
        errorMsg.value = false
        isLoadingReq.value = false
    }

    fun storeInSQLlite(list: List<Reqres>) {
        launch {

            reqDao.deleteReqres()
            var longList = reqDao.insertAllReqres(*list.toTypedArray())
            var i = 0;
            while (i < longList.size) {
                list[i].uuid = longList[i].toInt()
                i++
            }
            showData(list)
        }
        sharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}