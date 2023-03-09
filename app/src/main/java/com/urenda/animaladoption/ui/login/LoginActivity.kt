package com.urenda.animaladoption.ui.login

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
import com.urenda.animaladoption.ui.forgot_pass.Forgot_pass_Activity
import com.urenda.animaladoption.ui.home.HomeActivity
import com.urenda.animaladoption.ui.signup.SignupActivity

class LoginActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.setTitle("Iniciar Sesión")

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth

        //Declaration of buttons
        val btnLogin: Button = findViewById(R.id.btnLogin);
        val btnSignup: Button = findViewById(R.id.createAccountLink)
        val btnForgotPass: TextView = findViewById(R.id.forgotPassBtn)

        //Declaration fields
        val emailField: TextView = findViewById(R.id.emailField);
        val passwordField: TextView = findViewById(R.id.passwordField);

        //Login button
        btnLogin.setOnClickListener() {

            if (emailField.text.isNotEmpty() && passwordField.text.isNotEmpty())
            {
                login(emailField.text.toString(), passwordField.text.toString())
            }
            else
            {
                Toast.makeText(baseContext, "Uno o más campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        //Go to signup activity
        btnSignup.setOnClickListener() {

            signUpActivity()
        }

        //Go to forgotPassword activity
        btnForgotPass.setOnClickListener() {
            forgotPassActivity()
        }

    }

    //Redirect to home if credentials are correct
    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
            {
                if (firebaseAuth.currentUser!!.isEmailVerified)
                {
                    val homeActivity =  Intent(this, HomeActivity::class.java)
                    startActivity(homeActivity)
                    this.finish()
                }
                else
                {
                    Toast.makeText(baseContext, "Favor de verificar su correo", Toast.LENGTH_SHORT).show()
                }
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

    //Redirects to Forgot_pass_Activity
    private fun forgotPassActivity() {
        val forgotPassActivity = Intent(this, Forgot_pass_Activity::class.java)
        startActivity(forgotPassActivity)
        this.finish();
    }
}