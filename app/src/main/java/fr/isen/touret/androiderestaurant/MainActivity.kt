package fr.isen.touret.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView4 = findViewById(R.id.textView4) as TextView
        var textView5 = findViewById(R.id.textView5) as TextView
        var textView6 = findViewById(R.id.textView6) as TextView

        textView4.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page entrées", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("NomGlobal","Entrée")
            startActivity(intent)
        }


        textView5.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page plats", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("NomGlobal","Plats")
            startActivity(intent)
        }

        textView6.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "La page desserts", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("NomGlobal","Desserts")
            startActivity(intent)
        }

    }
}