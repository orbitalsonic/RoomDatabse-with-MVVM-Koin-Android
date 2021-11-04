package com.orbitalsonic.roomdatabasemvvmkoin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.roomdatabasemvvmkoin.R
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import com.orbitalsonic.roomdatabasemvvmkoin.interfaces.OnUserClickListener

class UserListAdapter:ListAdapter<UserEntity, UserListAdapter.UserViewHolder>(USER_COMPARATOR) {

    var mListener: OnUserClickListener? = null

    fun setOnItemClickListener(listener: OnUserClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_recycler_item, parent, false)
        return UserViewHolder(view,mListener!!)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.userName,current.cityName,current.mobileNumber)
    }


    class UserViewHolder(itemView: View, listener: OnUserClickListener) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tvUserName)
        private val cityName: TextView = itemView.findViewById(R.id.tvCityName)
        private val mobileNUmber:TextView = itemView.findViewById(R.id.tvMobileNumber)

        init {

            itemView.setOnClickListener{
                val mPosition = adapterPosition
                listener.onItemClick(mPosition)
            }

        }

        fun bind(mUserName: String,mCityName:String,mNumber:String) {
            userName.text = mUserName
            cityName.text =mCityName
            mobileNUmber.text = mNumber
        }

    }


    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}