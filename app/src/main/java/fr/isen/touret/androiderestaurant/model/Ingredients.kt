package fr.isen.touret.androiderestaurant.model

import java.io.Serializable

data class Ingredients(val id: Int, val id_shop: Int, val name_fr: String, val create_date: String, val update_date: String, val id_pizza: Int): Serializable
