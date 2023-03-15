package com.urenda.animaladoption.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.login.LoginActivity

class SignupActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var firestore: FirebaseFirestore
    private lateinit var checkAdministrator: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        //Declaration of buttons
        val btnSignup: Button = findViewById(R.id.btn_createAccount)
        val btnLogin: Button = findViewById(R.id.loginAccountLink)

        //Declaration fields
        val emailField: TextView = findViewById(R.id.newEmailField)
        val passwordField: TextView = findViewById(R.id.newPasswordField)
        val confirmPassField: TextView = findViewById(R.id.confirmPasswordField)

        //Administrator checkbox
        checkAdministrator = findViewById(R.id.checkAdministrator)

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

        //Go to login activity
        btnLogin.setOnClickListener() { LogInActivity() }

        if (resources.configuration.locale.language.equals("es"))
        {
            this.setTitle("Registrarse")
        }
        else
        {
            this.setTitle("Signin")
        }
    }

    //Create an account with the email and password provided
    private fun createAccount(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->

            if (task.isSuccessful)
            {
                sendEmailVerification()

                val userRol = hashMapOf(
                    "Administrator" to checkAdministrator.isChecked
                )

                firestore.collection("usersRol").document(email).set(userRol).addOnSuccessListener { documentReference ->

                    if (resources.configuration.locale.language.equals("es"))
                    {
                        Toast.makeText(baseContext, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(baseContext, "Account created successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                    .addOnFailureListener { e ->
                        Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
                    }
            }
            else
            {
                if (resources.configuration.locale.language.equals("es"))
                {
                    Toast.makeText(baseContext, "Ha ocurrido un error al crear la cuenta", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "An error occurred while creating the account", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    //Redirects to LoginActivity
    private fun LogInActivity() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        startActivity(loginActivity)
        this.finish();
    }

    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser

        user!!.sendEmailVerification().addOnCompleteListener(this) {  task ->
            if (task.isSuccessful)
            {
                if (resources.configuration.locale.language.equals("es"))
                {
                    Toast.makeText(baseContext, "Correo de verificación enviado", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "Verification email sent", Toast.LENGTH_SHORT).show()
                }
                LogInActivity()
            }
            else
            {
                if (resources.configuration.locale.language.equals("es"))
                {
                    Toast.makeText(baseContext, "Ha ocurrido un error al enviar el correo de verificación", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "An error occurred while sending the verification email", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}