package fr.isen.touret.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.touret.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.touret.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.touret.androiderestaurant.model.DataResult
import org.json.JSONObject
import kotlin.math.log


class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var customAdapter: CustomAdaptateur

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nomtitre = intent.getStringExtra("NomGlobal")
        binding.NomGlobal.text = nomtitre


        val itemsList = resources.getStringArray(R.array.dishes).toList()
        //recycleView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        customAdapter = CustomAdaptateur(arrayListOf()) {

        }
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

       loadDataFromServerByCategory( nomtitre ?: "")

    }

    companion object {
        val ITEM_KEY = "itemsList"
    }
    private fun loadDataFromServerByCategory(category: String){
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", 1)
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                binding.progressBar.isVisible = false
                val strResp = response.toString()
                val dataResult = Gson().fromJson(strResp, DataResult::class.java)

                val items = dataResult.data.firstOrNull { it.name_fr == category }?.items ?: arrayListOf()
                binding.recyclerView.adapter = CustomAdaptateur (items){
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(ITEM_KEY, it)
                    startActivity(intent)
                }

            }, {
                binding.progressBar.isVisible = false
                Log.d("API", "message ${it.message}")

            })
        queue.add(stringRequest)
    }
}
