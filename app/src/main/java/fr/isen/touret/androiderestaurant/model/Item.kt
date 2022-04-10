package fr.isen.touret.androiderestaurant.model

import java.io.Serializable

data class Item(val name_fr: String, val images: ArrayList<String>, val prices: ArrayList<Price>,     val id_category : String,
                val categ_name_fr : String,
                val categ_name_en : String, val ingredients: ArrayList<Ingredients>) : Serializable
