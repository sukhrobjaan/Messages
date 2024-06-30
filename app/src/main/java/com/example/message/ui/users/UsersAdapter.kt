package com.example.message.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.message.R
import com.example.message.core.dto.UserModel
import com.example.message.databinding.ItemUsersBinding

class UsersAdapter(
    private val context: Context,
) : ListAdapter<UserModel, UsersAdapter.UserViewHolder>(diffUtil) {

    inner class UserViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(usersModel: UserModel) {
            with(binding) {

                if (usersModel.userName.equals("Saqlangan xabarlar")){
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                    binding.ivUserProfile.setImageResource(R.drawable.img_save)
                }

                tvUserName.text = usersModel.userName.toString()
                tvUserToken.text = usersModel.userToken.toString()
            }

        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}