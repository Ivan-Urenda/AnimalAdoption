package com.urenda.animaladoption.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.home.HomeActivity
import com.urenda.animaladoption.ui.signup.SignupActivity

class LoginActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.setTitle("Iniciar Sesión")

        firebaseAuth = Firebase.auth

        //Declaration of buttons and fields
        val btnLogin: Button = findViewById(R.id.btnLogin);
        val btnSignup: Button = findViewById(R.id.createAccountLink)
        val emailField: TextView = findViewById(R.id.emailField);
        val passwordField: TextView = findViewById(R.id.passwordField);

        //Login button
        btnLogin.setOnClickListener() {
            login(emailField.text.toString(), passwordField.text.toString())
        }

        //Signup button
        btnSignup.setOnClickListener() {
            signUpActivity()
        }

    }


    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
            {
                val homeActivity =  Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)
                this.finish()
            }
            else
            {
                Toast.makeText(baseContext, "Error de email y/o contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Redirects to SignupActivity
    private fun signUpActivity() {
        val signUpActivity = Intent(this, SignupActivity::class.java)
        startActivity(signUpActivity)
        this.finish();
    }
}