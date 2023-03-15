package com.urenda.animaladoption.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.editAnimal.EditAnimalActivity
import com.urenda.animaladoption.ui.home.model.Animal

class AnimalsAdapter(private val AnimalsList:List<Animal>): RecyclerView.Adapter<AnimalsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return AnimalsViewHolder(layoutInflater.inflate(R.layout.item_animal, parent, false))
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {

        val item = AnimalsList[position]
        holder.render(item)

        //A listener is set to the card to edit the animal
        val card = holder.view.findViewById<CardView>(R.id.Cardv)
        card.setOnClickListener() {
            val editAnimalActivity = Intent(holder.view.context, EditAnimalActivity::class.java)

            //The id of the animal (id of the document) is passed
            editAnimalActivity.putExtra("id", item.Id)

            holder.view.context.startActivity(editAnimalActivity)
        }
    }

    override fun getItemCount(): Int = AnimalsList.size

}