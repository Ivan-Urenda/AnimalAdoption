package com.urenda.animaladoption.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.urenda.animaladoption.R

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.setTitle("Inicio")
    }
}