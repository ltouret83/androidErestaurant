package fr.isen.touret.androiderestaurant.model

import java.io.Serializable

data class Ingredients(val id: String, val id_shop: String, val name_fr: String,val name_en: String, val create_date: String, val update_date: String, val id_pizza: String): Serializable
