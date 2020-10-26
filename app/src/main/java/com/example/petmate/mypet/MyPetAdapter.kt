package com.example.petmate.mypet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import kotlinx.android.synthetic.main.layout_my_pet.view.*

class MyPetAdapter(private val myPet: List<Pet>) :
    RecyclerView.Adapter<MyPetAdapter.MyPetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPetViewHolder {
        return MyPetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_my_pet, parent, false)
        )
    }

    override fun getItemCount() = myPet.size

    override fun onBindViewHolder(holder: MyPetViewHolder, position: Int) {
        val pet = myPet[position]

        holder.view.petBreedName.text = pet.language
        holder.view.petName.text = pet.title
        holder.view.petStatus.text = pet.type
        holder.view.petNo.text = pet.id.toString()

        Glide.with(holder.view.context)
            .load(pet.image)
            .into(holder.view.imageViewMyPet)
    }

    class MyPetViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}