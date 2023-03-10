package com.urenda.animaladoption.ui.editAnimal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.urenda.animaladoption.R

class EditAnimalActivity: AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addanimal)
        this.setTitle("Agregar Animal")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        if (resources.configuration.locale.language.equals("es"))
        {
            this.setTitle("Editar Animal")
        }
        else
        {
            this.setTitle("Edit Animal")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return false;
    }
}