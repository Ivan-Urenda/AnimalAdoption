package com.urenda.animaladoption.ui.editAnimal

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.home.model.Animal

class EditAnimalActivity: AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var animalNameField: TextView
    private lateinit var animalKindField: TextView
    private lateinit var animalGenderSpinner: Spinner
    private lateinit var animalAgeField: TextView
    private lateinit var animalBreedField: TextView
    private lateinit var animalSizeSpinner: Spinner
    private lateinit var animalContactField: TextView

    private lateinit var btnAdd: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editanimal)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getString("id")

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        //Declaration of buttons
        btnAdd = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)

        //Declaration fields
        animalNameField = findViewById(R.id.AnimalNameField)
        animalKindField = findViewById(R.id.AnimalKindField)
        animalGenderSpinner = findViewById(R.id.AnimalGenderSpinner)
        animalAgeField = findViewById(R.id.AnimalAgeField)
        animalBreedField = findViewById(R.id.AnimalBreedField)
        animalSizeSpinner = findViewById(R.id.AnimalSizeSpinner)
        animalContactField = findViewById(R.id.AnimalContactField)

        loadSpinnerGender(animalGenderSpinner)
        loadSpinnerSize(animalSizeSpinner)

        getAnimal(id.toString())

        btnAdd.setOnClickListener() { saveAnimal(animalNameField, animalKindField, animalGenderSpinner, animalAgeField, animalBreedField, animalSizeSpinner, id.toString()) }
        btnDelete.setOnClickListener() { alertDialog(id.toString()) }

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

    private fun getAnimal(id: String) {

        firestore.collection("animals").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null)
                {
                    if (resources.configuration.locale.language.equals("es"))
                    {
                        var gender = document["Gender"].toString()
                        var size = document["Size"].toString()

                        gender = if (document["Gender"].toString().equals("Male")) "Macho" else gender
                        gender = if (document["Gender"].toString().equals("Female")) "Hembra" else gender

                        size = if (document["Size"].toString().equals("Small")) "Pequeño" else size
                        size = if (document["Size"].toString().equals("Medium")) "Mediano" else size
                        size = if (document["Size"].toString().equals("Big")) "Grande" else size

                        val animal = Animal(document["Age"].toString(), document["Breed"].toString(), document["Gender"].toString(),
                            document["Kind"].toString(), document["Name"].toString(), document["Size"].toString(), document.id, document["owner"].toString())

                        setData(animal)
                    }
                    else
                    {
                        var gender = document["Gender"].toString()
                        var size = document["Size"].toString()

                        gender = if (document["Gender"].toString().equals("Macho")) "Male" else gender
                        gender = if (document["Gender"].toString().equals("Hembra")) "Female" else gender

                        size = if (document["Size"].toString().equals("Pequeño")) "Small" else size
                        size = if (document["Size"].toString().equals("Mediano")) "Medium" else size
                        size = if (document["Size"].toString().equals("Grande")) "Big" else size


                        val animal = Animal(document["Age"].toString(), document["Breed"].toString(), gender,
                            document["Kind"].toString(), document["Name"].toString(), size, document.id, document["owner"].toString())

                        setData(animal)
                    }
                }
                else
                {
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loadSpinnerGender(spinner: Spinner){

        val list = if(resources.configuration.locale.language.equals("es")) listOf("Macho", "Hembra") else listOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.spinner_items, list)
        spinner.adapter = adapter
    }

    private fun loadSpinnerSize(spinner: Spinner){

        val list = if(resources.configuration.locale.language.equals("es")) listOf("Pequeño", "Mediano", "Grande") else listOf("Small", "Medium", "Big")
        val adapter = ArrayAdapter(this, R.layout.spinner_items, list)
        spinner.adapter = adapter
    }

    private fun setData(animal: Animal) {

        if (resources.configuration.locale.language.equals("es"))
        {
            animalNameField.text = animal.Name
            animalKindField.text = animal.Kind
            if (animal.Gender.equals("Macho"))
            {
                animalGenderSpinner.setSelection(0)
            }
            else
            {
                animalGenderSpinner.setSelection(1)
            }
            animalAgeField.text = animal.Age
            animalBreedField.text = animal.Breed
            if (animal.Size.equals("Pequeño"))
            {
                animalSizeSpinner.setSelection(0)
            }
            else if(animal.Size.equals("Mediano"))
            {
                animalSizeSpinner.setSelection(1)
            }
            else
            {
                animalSizeSpinner.setSelection(2)
            }


        }
        else
        {
            animalNameField.text = animal.Name
            animalKindField.text = animal.Kind
            if (animal.Gender.equals("Male"))
            {
                animalGenderSpinner.setSelection(0)
            }
            else
            {
                animalGenderSpinner.setSelection(1)
            }
            animalAgeField.text = animal.Age
            animalBreedField.text = animal.Breed
            if (animal.Size.equals("Small"))
            {
                animalSizeSpinner.setSelection(0)
            }
            else if(animal.Size.equals("Medium"))
            {
                animalSizeSpinner.setSelection(1)
            }
            else
            {
                animalSizeSpinner.setSelection(2)
            }
            animalContactField.text = animal.Contact
        }

        animalContactField.text = animal.Contact
        animalContactField.isEnabled = false
        animalContactField.isCursorVisible = false
        animalContactField.keyListener = null
        animalContactField.setBackgroundColor(Color.TRANSPARENT)

        if(!animal.Contact.equals(firebaseAuth.currentUser?.email.toString()))
        {
            animalNameField.isEnabled = false
            animalNameField.isCursorVisible = false
            animalNameField.keyListener = null
            animalNameField.setBackgroundColor(Color.TRANSPARENT)

            animalKindField.isEnabled = false
            animalKindField.isCursorVisible = false
            animalKindField.keyListener = null
            animalKindField.setBackgroundColor(Color.TRANSPARENT)

            animalGenderSpinner.isEnabled = false
            animalGenderSpinner.setBackgroundColor(Color.TRANSPARENT)

            animalAgeField.isEnabled = false
            animalAgeField.isCursorVisible = false
            animalAgeField.keyListener = null
            animalAgeField.setBackgroundColor(Color.TRANSPARENT)

            animalBreedField.isEnabled = false
            animalBreedField.isCursorVisible = false
            animalBreedField.keyListener = null
            animalBreedField.setBackgroundColor(Color.TRANSPARENT)

            animalSizeSpinner.isEnabled = false
            animalSizeSpinner.setBackgroundColor(Color.TRANSPARENT)

            btnAdd.isEnabled = false
            btnDelete.isEnabled = false
        }


    }

    private fun saveAnimal(animalNameField: TextView, animalKindField: TextView, animalGenderSpinner: Spinner,
                           animalAgeField: TextView, animalBreedField: TextView, animalSizeSpinner: Spinner, id: String) {

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


            firestore.collection("animals").document(id).update(animal as Map<String, Any>).addOnSuccessListener { documentReference ->

                if (resources.configuration.locale.language.equals("es"))
                {
                    Toast.makeText(baseContext, "Animal actualizado correctamente", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "Animal updated successfully", Toast.LENGTH_SHORT).show()
                }


                this.finish()
            }
                .addOnFailureListener { e ->
                    Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
                }
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

    private fun deleteAnimal(id: String) {

        firestore.collection("animals").document(id).delete().addOnSuccessListener { documentReference ->

            if (resources.configuration.locale.language.equals("es"))
            {
                Toast.makeText(baseContext, "Animal eliminado correctamente", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(baseContext, "Animal deleted successfully", Toast.LENGTH_SHORT).show()
            }


            this.finish()
        }
            .addOnFailureListener { e ->
                Toast.makeText(baseContext, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    private fun alertDialog(id: String) {

        if (resources.configuration.locale.language.equals("es"))
        {
            AlertDialog.Builder(this).apply {
                setTitle("Alerta")
                setMessage("¿Está seguro que desea eliminar este animal?")
                setPositiveButton("Si") { _: DialogInterface, _: Int ->
                    deleteAnimal(id)
                }
                setNegativeButton("No", null)
            }.show()
        }
        else
        {
            AlertDialog.Builder(this).apply {
                setTitle("Alert")
                setMessage("Are you sure you want to delete this animal?")
                setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                    deleteAnimal(id)
                }
                setNegativeButton("No", null)
            }.show()
        }
    }
}