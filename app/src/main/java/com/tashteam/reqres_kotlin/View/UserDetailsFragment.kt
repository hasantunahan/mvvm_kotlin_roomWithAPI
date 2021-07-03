package com.tashteam.reqres_kotlin.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tashteam.reqres_kotlin.app.Extensions.glideWithExtensions
import com.tashteam.reqres_kotlin.app.Extensions.progressDrawable
import com.tashteam.reqres_kotlin.R
import com.tashteam.reqres_kotlin.ViewModel.ReqDetailViewModel
import com.tashteam.reqres_kotlin.databinding.FragmentUserDetailsBinding
import kotlinx.android.synthetic.main.fragment_user_details.*


class UserDetailsFragment : Fragment() {

    private lateinit var detailViewModel: ReqDetailViewModel
    private var reqUUID = 0
    private lateinit var bindingReqData: FragmentUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingReqData =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        return bindingReqData.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProviders.of(this).get(ReqDetailViewModel::class.java)

        arguments?.let {
            reqUUID = UserDetailsFragmentArgs.fromBundle(it).uuid
        }

        detailViewModel.getDetailsData(reqUUID)
        getDataRoom()

    }

    fun getDataRoom() {
        detailViewModel.reqDetail.observe(viewLifecycleOwner, Observer { detail ->
            detail?.let {
                bindingReqData.selectedData = it
            }
        })
    }

}