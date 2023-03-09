package com.urenda.animaladoption.ui.signup

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
import com.urenda.animaladoption.ui.login.LoginActivity

class SignupActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        this.setTitle("Registrarse")

        firebaseAuth = Firebase.auth

        val btnSignup: Button = findViewById(R.id.btn_createAccount)
        val btnLogin: Button = findViewById(R.id.loginAccountLink)

        val emailField: TextView = findViewById(R.id.newEmailField)
        val passwordField: TextView = findViewById(R.id.newPasswordField)
        val confirmPassField: TextView = findViewById(R.id.confirmPasswordField)

        //Signup button
        btnSignup.setOnClickListener() {

            if (emailField.text.isNotEmpty() && passwordField.text.isNotEmpty() && confirmPassField.text.isNotEmpty())
            {
                var pass1 = passwordField.text.toString()
                var pass2 = confirmPassField.text.toString()
                var email = emailField.text.toString()

                if (pass1.equals(pass2))
                {
                    createAccount(email, pass1)
                }
                else
                {
                    Toast.makeText(baseContext, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(baseContext, "Uno o más campos vacíos", Toast.LENGTH_SHORT).show()
            }

        }

        //Login button
        btnLogin.setOnClickListener() { LogInActivity() }
    }

    private fun createAccount(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->

            if (task.isSuccessful)
            {
                Toast.makeText(baseContext, "Cuenta creada con exito", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(baseContext, "Ha ocurrido un error al crear la cuenta", Toast.LENGTH_SHORT).show()
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