package com.urenda.animaladoption.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urenda.animaladoption.R
import com.urenda.animaladoption.ui.home.model.Animal

class AnimalsAdapter(private val AnimalsList:List<Animal>): RecyclerView.Adapter<AnimalsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return AnimalsViewHolder(layoutInflater.inflate(R.layout.item_animal, parent, false))
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {

        val item = AnimalsList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = AnimalsList.size

}