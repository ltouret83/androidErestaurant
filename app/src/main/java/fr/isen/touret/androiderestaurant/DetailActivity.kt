package fr.isen.touret.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.touret.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.touret.androiderestaurant.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detaiTitle.text = intent.getStringExtra(CategoryActivity.ITEM_KEY)
    }

}