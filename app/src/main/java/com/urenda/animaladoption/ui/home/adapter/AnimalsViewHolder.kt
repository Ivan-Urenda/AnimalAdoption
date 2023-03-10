package com.urenda.animaladoption.ui.home.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.home.model.Animal

class AnimalsViewHolder(view:View): RecyclerView.ViewHolder(view) {

    val animalName = view.findViewById<TextView>(R.id.AnimalName)
    val animalAge = view.findViewById<TextView>(R.id.AnimalAge)
    val animalGender = view.findViewById<TextView>(R.id.AnimalGender)
    val animalSize = view.findViewById<TextView>(R.id.AnimalSize)

    fun render(animalModel: Animal) {

        animalName.text = animalModel.Name
        animalAge.text = animalModel.Age
        animalGender.text = animalModel.Gender
        animalSize.text = animalModel.Size
    }
}