package fr.isen.touret.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.touret.androiderestaurant.ble.BLEScanActivity
import fr.isen.touret.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "LaurenaAndroidERestaurant"

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var buttonEntrees = binding.buttonEntrees
        var buttonPlats = binding.buttonPlats
        var buttonDesserts = binding.buttonDesserts
        var buttonBLE = binding.buttonBLE

        buttonEntrees.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page des entrées", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("Category", "Entrées")
            startActivity(intent)
        }
        buttonPlats.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page plats", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("Category", "Plats")
            startActivity(intent)
        }
        buttonDesserts.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page desserts", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("Category", "Desserts")
            startActivity(intent)
        }
        buttonBLE.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page bluetooth", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BLEScanActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy", "onDestroy: ")
    }
}