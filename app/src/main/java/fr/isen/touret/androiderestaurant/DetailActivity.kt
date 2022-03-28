package fr.isen.touret.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.touret.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.touret.androiderestaurant.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(CategoryActivity.ITEM_KEY) as Item
        binding.detaiTitle.text = item.name_fr

        binding.ingredientView.text = item.ingredients.joinToString(", ") { it.name_fr}

        val carouselAdapter = CarouselAdapter(this, item.images)
        binding.detailSlider.adapter = carouselAdapter

        binding.moinsView.setOnClickListener{

        }


    }
    private fun goToBuy(){


    }

}