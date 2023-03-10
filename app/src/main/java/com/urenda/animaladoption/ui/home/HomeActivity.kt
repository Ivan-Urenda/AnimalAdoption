package com.urenda.animaladoption.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.addAnimal.AddAnimalActivity
import com.urenda.animaladoption.ui.home.adapter.AnimalsAdapter
import com.urenda.animaladoption.ui.home.model.Animal
import com.urenda.animaladoption.ui.login.LoginActivity

class HomeActivity: AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var firestore: FirebaseFirestore
    private lateinit var AnimalsList: MutableList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.setTitle("Adopción Animal")

        //Initialize the variable to be able to use it
        firebaseAuth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        //Turn off dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Declaration of buttons
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        //initRecyclerView()

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

    private fun initRecyclerView() {

        AnimalsList = mutableListOf()

        firestore.collection("animals")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val animal = Animal("Edad: "+document["Age"].toString(), "Especie: "+document["Breed"].toString(), "Genero: "+document["Gender"].toString(),
                        "Raza: "+document["Kind"].toString(), document["Name"].toString(), "Tamaño: "+document["Size"].toString())
                    AnimalsList.add(animal)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.reciclerViewAnimals)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = AnimalsAdapter(AnimalsList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(baseContext, exception.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }
}