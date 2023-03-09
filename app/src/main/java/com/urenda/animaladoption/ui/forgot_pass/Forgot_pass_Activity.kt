package com.urenda.animaladoption.ui.forgot_pass

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.login.LoginActivity

class Forgot_pass_Activity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        this.setTitle("Recuperar contraseña")

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth

        //Declaration of buttons
        val btnLogin: Button = findViewById(R.id.loginAccountAfterLink)
        val btnDone: Button = findViewById(R.id.passwordResetBtn)

        //Declaration of fields
        val emailField: TextView = findViewById(R.id.emailPasswordReset)

        //Done button
        btnDone.setOnClickListener() {

            if (emailField.text.isNotEmpty())
            {
                sendPasswordReset(emailField.text.toString())
            }
            else
            {
                Toast.makeText(baseContext, "El campo de correo no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        //Go to login activity
        btnLogin.setOnClickListener() { LogInActivity() }
    }

    //Send an email to change the password
    private fun sendPasswordReset(email: String){

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(this) { task ->

            if (task.isSuccessful)
            {
                Toast.makeText(baseContext, "¡Correo de cambio de contraseña enviado!", Toast.LENGTH_SHORT).show()
                LogInActivity()
            }
            else
            {
                Toast.makeText(baseContext, "Correo no registrado", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //Redirects to LoginActivity
    private fun LogInActivity() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        startActivity(loginActivity)
        this.finish();
    }
}