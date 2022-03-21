package fr.isen.touret.androiderestaurant

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var nomGlobal = intent.getStringExtra("NomGlobal")
        var categoryNom = findViewById(R.id.categoryNom) as TextView
         categoryNom.text = nomGlobal


    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy","onDestroy: ")
    }
}