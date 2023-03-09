package com.urenda.animaladoption.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.addAnimal.AddAnimalActivity
import com.urenda.animaladoption.ui.login.LoginActivity

class HomeActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.setTitle("Adopción Animal")

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Declaration of buttons
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnAdd.setOnClickListener() { addAnimalActivity() }
        btnLogout.setOnClickListener() { Logout() }
    }

    //Redirects to AddAnimalActivity
    private fun addAnimalActivity() {
        val addAnimalActivity = Intent(this, AddAnimalActivity::class.java)
        startActivity(addAnimalActivity)
    }

    //Close user session and redirect you to login
    private fun Logout() {
        firebaseAuth.signOut()
        val loginActivity = Intent(this, LoginActivity::class.java)
        startActivity(loginActivity)
        this.finish()
    }
}