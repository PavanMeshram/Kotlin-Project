package com.example.petmate.mypet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import kotlinx.android.synthetic.main.activity_my_pets.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPets : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pets)
        supportActionBar!!.title = "My Pet"

        val colorDrawable = ColorDrawable(Color.parseColor("#FFC107"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navViewMypet.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item1 -> Toast.makeText(
                    applicationContext,
                    "Clicked Item 1",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.item2 -> Toast.makeText(
                    applicationContext,
                    "Clicked Item 2",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.item3 -> Toast.makeText(
                    applicationContext,
                    "Clicked Item 3",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        refreshLayout.setOnRefreshListener {
            fetchMyPet()
        }

        fetchMyPet()
    }

    private fun fetchMyPet() {
        refreshLayout.isRefreshing = true

        MyPetApi().getMyPets().enqueue(object : Callback<List<Pet>> {
            override fun onFailure(call: Call<List<Pet>>, t: Throwable) {
                refreshLayout.isRefreshing = false
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Pet>>, response: Response<List<Pet>>) {
                refreshLayout.isRefreshing = false
                val myPet = response.body()

                myPet?.let {
                    showMyPets(it)
                }

            }
        })
    }

    private fun showMyPets(myPet: List<Pet>) {
        recyclerViewMyPet.layoutManager = LinearLayoutManager(this)
        recyclerViewMyPet.adapter = MyPetAdapter(myPet)
    }

}