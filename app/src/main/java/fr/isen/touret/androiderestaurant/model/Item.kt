package fr.isen.touret.androiderestaurant.model

import java.io.Serializable

data class Item(
    val name_fr: String,
    val images: Array<String>,
    val prices: Array<Price>,
    val id_category : String,
    val categ_name_fr : String,
    val categ_name_en : String,
    val ingredients: Array<Ingredients>) : Serializable
