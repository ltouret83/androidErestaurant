package fr.isen.touret.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.touret.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeEntrees.setOnClickListener{
            goToCategory(getString(R.string.entrres))
        }


        binding.homePlats.setOnClickListener{
            goToCategory(getString(R.string.plats))
        }

        binding.homeDesserts.setOnClickListener{
            goToCategory(getString(R.string.desserts))
        }


    }
    private fun goToCategory(category: String){
        val intent = Intent(this, CategoryActivity:: class.java)
        Toast.makeText(
            this@HomeActivity,
            category,
            Toast.LENGTH_SHORT
        ).show()
        intent.putExtra("NomGlobal", category)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivité","mon activité est détruit: ")
    }
}