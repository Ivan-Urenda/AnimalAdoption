package com.urenda.animaladoption.ui.addAnimal

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import java.util.*
import kotlin.collections.HashMap

class AddAnimalActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addanimal)
        this.setTitle("Agregar Animal")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        //Declaration of buttons
        val btnAdd: Button = findViewById(R.id.btnSave)

        //Declaration fields
        val animalNameField: TextView = findViewById(R.id.AnimalNameField)
        val animalKindField: TextView = findViewById(R.id.AnimalKindField)
        val animalGenderSpinner: Spinner = findViewById(R.id.AnimalGenderSpinner)
        val animalAgeField: TextView = findViewById(R.id.AnimalAgeField)
        val animalBreedField: TextView = findViewById(R.id.AnimalBreedField)
        val animalSizeSpinner: Spinner = findViewById(R.id.AnimalSizeSpinner)

        loadSpinnerGender(animalGenderSpinner)
        loadSpinnerSize(animalSizeSpinner)

        btnAdd.setOnClickListener() { saveAnimal(animalNameField, animalKindField, animalGenderSpinner, animalAgeField, animalBreedField, animalSizeSpinner) }
    }

    private fun saveAnimal(animalNameField: TextView, animalKindField: TextView, animalGenderSpinner: Spinner,
                           animalAgeField: TextView, animalBreedField: TextView, animalSizeSpinner: Spinner) {

        var animalName = animalNameField.text
        var animalKind = animalKindField.text
        var animalGender = animalGenderSpinner.selectedItem.toString()
        var animalAge = animalAgeField.text
        var animalBreed = animalBreedField.text
        var animalSize = animalSizeSpinner.selectedItem.toString()

        if (animalName.isNotEmpty() && animalKind.isNotEmpty() && animalGender.isNotEmpty()
            && animalAge.isNotEmpty() && animalBreed.isNotEmpty() && animalSize.isNotEmpty())
        {

            val animal = hashMapOf(
                "Name" to animalName.toString(),
                "Kind" to animalKind.toString(),
                "Gender" to animalGender,
                "Age" to animalAge.toString(),
                "Breed" to animalBreed.toString(),
                "Size" to animalSize
            )


            firestore.collection("animals").add(animal).addOnSuccessListener { documentReference ->

                Toast.makeText(baseContext, "Animal agregado correctamente", Toast.LENGTH_SHORT).show()
                this.finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(baseContext, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(baseContext, "Uno o más campos vacíos", Toast.LENGTH_SHORT).show()
        }

    }


    private fun loadSpinnerGender(spinner: Spinner){
        val list = listOf("Macho", "Hembra")

        val adapter = ArrayAdapter(this, R.layout.spinner_items, list)
        spinner.adapter = adapter
    }

    private fun loadSpinnerSize(spinner: Spinner){
        val list = listOf("Pequeño", "Mediano", "Grande")

        val adapter = ArrayAdapter(this, R.layout.spinner_items, list)
        spinner.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return false;
    }
}