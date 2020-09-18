package com.example.kotlinpractise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpractise.databinding.CardViewProfileTinderBinding

class ProfilesAdapter : RecyclerView.Adapter<ProfilesAdapter.ProfileViewHolder>() {

    private var profiles: List<Profile>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_view_profile_tinder,
            parent,
            false
        )
    )

    override fun getItemCount() = profiles?.size ?: 0

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        profiles?.let {
            holder.binding.profile = it[position]
            holder.binding.executePendingBindings()
        }
    }

    fun setProfiles(profiles: List<Profile>) {
        this.profiles = profiles
        notifyDataSetChanged()
    }

    inner class ProfileViewHolder(val binding: CardViewProfileTinderBinding) :
        RecyclerView.ViewHolder(binding.root)

}