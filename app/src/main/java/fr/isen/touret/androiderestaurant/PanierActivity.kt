package fr.isen.touret.androiderestaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.touret.androiderestaurant.adapter.PanierAdapter
import fr.isen.touret.androiderestaurant.databinding.ActivityPanierBinding
import fr.isen.touret.androiderestaurant.model.Panier
import fr.isen.touret.androiderestaurant.model.PanierLine
import java.io.File

class PanierActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPanierBinding
    private val itemsList = ArrayList<PanierLine>()
    private lateinit var panierAdapter: PanierAdapter
    private lateinit var panier: Panier


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanierBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        panier = PanierRead()
        //titre fenetre
        title = "Votre panier"

        //setup du recycler view
        val recyclerPanier: RecyclerView = binding.recyclerPanier
        panierAdapter = PanierAdapter(itemsList, PanierAdapter.OnClickListener { item ->
            onListCartClickDelete(item)
        })
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerPanier.layoutManager = layoutManager
        recyclerPanier.adapter = panierAdapter
        panier.lignes.forEach { ligne: PanierLine-> itemsList.add(ligne) }
        panierAdapter.notifyDataSetChanged()

        //prix
        updateTotalPrice()

        val buyButton = binding.buttonTotalPrice

        buyButton.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "You buy it", Toast.LENGTH_SHORT).show()
        }

    }


    private fun updateTotalPrice(){
        var totalPrice = 0.0F
        this.panier.lignes.forEach { ligne:PanierLine -> totalPrice += ligne.Item.prices[0].price.toFloat() * ligne.quantite }

        if(totalPrice == 0.0F){
            binding.buttonTotalPrice.text = "Vous n'avez rien dans votre panier"
        }
        else{
            binding.buttonTotalPrice.text = "Payez " + totalPrice.toString() + " €"
        }
    }

    private fun onListCartClickDelete(item: PanierLine) {
        Toast.makeText(this@PanierActivity, "${item.quantite} ${item.Item.name_fr} enlevé(s) du panier",Toast.LENGTH_SHORT).show()
        this.itemsList.remove(item)
        this.panier.lignes.remove(item)
        Log.d("PANIER",panier.lignes.size.toString())
        this.PanierWrite()
        this.updateTotalPrice()
        this.panierAdapter.notifyDataSetChanged()
    }

    private fun PanierWrite(){
        //sauvegarde du panier en json dans les fichiers
        val panierJson = Gson().toJson(panier)
        val filename = "panier.json"
        this.binding.root.context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(panierJson.toByteArray())
        }
    }

    private fun PanierRead(): Panier {
        //lecture fichier panier
        val filename = "panier.json"
        val file = File(binding.root.context.filesDir, filename)
        if(file.exists()){
            val contents = file.readText()
            return Gson().fromJson(contents, Panier::class.java)
        }else{
            return Panier(ArrayList())
        }
    }

}