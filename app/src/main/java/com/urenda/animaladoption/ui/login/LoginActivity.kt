package com.urenda.animaladoption.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.forgot_pass.Forgot_pass_Activity
import com.urenda.animaladoption.ui.home.HomeActivity
import com.urenda.animaladoption.ui.signup.SignupActivity
import java.util.*

class LoginActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var btnForgotPass: TextView
    private lateinit var emailField: TextView
    private lateinit var passwordField: TextView
    private lateinit var registerTxt: TextView
    private lateinit var emailTxt: TextInputLayout
    private lateinit var passwordTxt: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.setTitle("Iniciar Sesión")

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth

        //Declaration of buttons
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.createAccountLink)
        btnForgotPass = findViewById(R.id.forgotPassBtn)

        //Declaration of switch
        val switch: Switch = findViewById(R.id.switchLang)

        //Declaration fields
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        registerTxt = findViewById(R.id.tvRegisterText)
        emailTxt = findViewById(R.id.emailFieldLayout)
        passwordTxt = findViewById(R.id.passwordFieldLayout)

        //Login button
        btnLogin.setOnClickListener() {

            if (emailField.text.isNotEmpty() && passwordField.text.isNotEmpty())
            {
                login(emailField.text.toString(), passwordField.text.toString())
            }
            else
            {
                if (resources.configuration.locale.language.equals("es"))
                {
                    Toast.makeText(baseContext, "Uno o más campos vacíos", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "One or more empty fields", Toast.LENGTH_SHORT).show()
                }
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

        if (resources.configuration.locale.language.equals("es"))
        {
            switch.isChecked = true
        }

        switch.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked)
            {
                //Toast.makeText(baseContext, "Español", Toast.LENGTH_SHORT).show()
                language("es")
                this.setTitle("Iniciar Sesión")
            }
            else
            {
                //Toast.makeText(baseContext, "English", Toast.LENGTH_SHORT).show()
                language("en")
                this.setTitle("Login")
            }
        }

    }

    private fun language(lang: String) {

        val resources = resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.setLocale(Locale(lang))
        resources.updateConfiguration(configuration, displayMetrics)
        configuration.locale = Locale(lang)
        resources.updateConfiguration(configuration, displayMetrics)

        //Toast.makeText(baseContext, resources.configuration.locale.language, Toast.LENGTH_SHORT).show()

        btnLogin.text = resources.getString(R.string.btn_login)
        btnSignup.text = resources.getString(R.string.btn_create_account)
        btnForgotPass.text = resources.getString(R.string.forgot_pass)
        emailTxt.hint = resources.getString(R.string.email_hint)
        passwordTxt.hint = resources.getString(R.string.password_hint)
        registerTxt.text = resources.getString(R.string.register_text)

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
                    if (resources.configuration.locale.language.equals("es"))
                    {
                        Toast.makeText(baseContext, "Favor de verificar su correo", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(baseContext, "Please check your email", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                if (resources.configuration.locale.language.equals("es"))
                {
                    Toast.makeText(baseContext, "Error de email y/o contraseña", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                }
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