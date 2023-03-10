package com.urenda.animaladoption.ui.home.adapter

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.editAnimal.EditAnimalActivity
import com.urenda.animaladoption.ui.home.HomeActivity
import com.urenda.animaladoption.ui.home.model.Animal
import com.urenda.animaladoption.ui.login.LoginActivity

class AnimalsViewHolder(val view:View): RecyclerView.ViewHolder(view) {

    var documentSnapshot = adapterPosition.toString()

    val animalName = view.findViewById<TextView>(R.id.AnimalName)
    val animalAge = view.findViewById<TextView>(R.id.AnimalAge)
    val animalGender = view.findViewById<TextView>(R.id.AnimalGender)
    val animalSize = view.findViewById<TextView>(R.id.AnimalSize)
    //val card = view.findViewById<CardView>(R.id.Cardv)

    fun render(animalModel: Animal) {

        animalName.text = animalModel.Name
        animalAge.text = animalModel.Age
        animalGender.text = animalModel.Gender
        animalSize.text = animalModel.Size

       // card.setOnClickListener() {
       //     val editAnimalActivity = Intent(view.context, EditAnimalActivity::class.java)
        //    view.context.startActivity(editAnimalActivity)
        //    editAnimalActivity.putExtra("id", animalModel.Name)
        //}
    }


}