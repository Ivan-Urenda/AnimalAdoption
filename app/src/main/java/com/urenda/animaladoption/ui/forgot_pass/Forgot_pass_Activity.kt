package com.urenda.animaladoption.ui.forgot_pass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.urenda.animaladoption.R

class Forgot_pass_Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.setTitle("Recuperar contraseña")
    }
}