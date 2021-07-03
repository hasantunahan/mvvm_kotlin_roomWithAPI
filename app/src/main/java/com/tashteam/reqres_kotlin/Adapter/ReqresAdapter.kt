package com.tashteam.reqres_kotlin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tashteam.reqres_kotlin.app.listener.ItemClickListener
import com.tashteam.reqres_kotlin.model.Reqres
import com.tashteam.reqres_kotlin.R
import com.tashteam.reqres_kotlin.View.UserFragmentDirections
import com.tashteam.reqres_kotlin.databinding.ItemReqresBinding
import kotlinx.android.synthetic.main.item_reqres.view.*


class ReqresAdapter(val reqList: ArrayList<Reqres>) :
        RecyclerView.Adapter<ReqresAdapter.MyViewHolder>(), ItemClickListener {

    class MyViewHolder(var view: ItemReqresBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemReqresBinding>(
                inflater,
                R.layout.item_reqres,
                parent,
                false
        )
        return MyViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.data = reqList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return reqList.size
    }

    fun refreshData(newReqList: List<Reqres>) {
        reqList.clear()
        reqList.addAll(newReqList)
        notifyDataSetChanged()
    }

    override fun setItemClickListener(view: View) {
        var uuid = view.reqresImageUUID.text.toString().toInt()
        val action = UserFragmentDirections.actionUserFragment2ToUserDetailsFragment2(uuid)
        Navigation.findNavController(view).navigate(action)
    }

}