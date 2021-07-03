package com.tashteam.reqres_kotlin.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tashteam.reqres_kotlin.Adapter.ReqresAdapter
import com.tashteam.reqres_kotlin.R
import com.tashteam.reqres_kotlin.View.UserFragmentDirections
import com.tashteam.reqres_kotlin.ViewModel.ReqresViewModel
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : Fragment() {

    private lateinit var reqViewmodel: ReqresViewModel
    private val adapter = ReqresAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reqViewmodel = ViewModelProviders.of(this).get(ReqresViewModel::class.java)
        reqViewmodel.refreshData()

        reqListRecylerView.layoutManager = LinearLayoutManager(context)
        reqListRecylerView.adapter = adapter
        getLiveData()

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            reqListRecylerView.visibility = View.GONE
            errMsgTextView.visibility = View.GONE
            loadingProggresBar.visibility = View.VISIBLE
            reqViewmodel.refreshAPI()
        }
    }

    fun getLiveData() {
        reqViewmodel.reqList.observe(viewLifecycleOwner, Observer { reqlist ->
            adapter.refreshData(reqlist)
            reqListRecylerView.visibility = View.VISIBLE
            loadingProggresBar.visibility = View.GONE
            errMsgTextView.visibility = View.GONE

        })

        reqViewmodel.errorMsg.observe(viewLifecycleOwner, Observer { err ->
            err?.let {
                if (err) {
                    reqListRecylerView.visibility = View.GONE
                    loadingProggresBar.visibility = View.GONE
                    errMsgTextView.visibility = View.VISIBLE

                }
            }

        })

        reqViewmodel.isLoadingReq.observe(viewLifecycleOwner, Observer { loading ->

            loading?.let {
                if (loading) {
                    reqListRecylerView.visibility = View.GONE
                    errMsgTextView.visibility = View.GONE
                    loadingProggresBar.visibility = View.VISIBLE

                }
            }
        })
    }

}