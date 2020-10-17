package com.example.petmate

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class PetRegistration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_registration)
        supportActionBar!!.title = "Pet Registration"

        val colorDrawable = ColorDrawable(Color.parseColor("#FFC107"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
        supportActionBar?.apply {
        }
    }
}