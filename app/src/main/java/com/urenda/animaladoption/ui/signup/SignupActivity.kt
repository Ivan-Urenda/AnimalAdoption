package com.urenda.animaladoption.ui.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.urenda.animaladoption.R

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        this.setTitle("Registrarse")
    }
}