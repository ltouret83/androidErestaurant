package fr.isen.touret.androiderestaurant
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.touret.androiderestaurant.model.Item

internal class CustomAdaptateur (private var itemsList: List<Item>, val clickListener: (Item) -> Unit) :
    RecyclerView.Adapter<CustomAdaptateur.MyViewHolder>() {
        internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var itemTextView: TextView = view.findViewById(R.id.itemTextView)
            var itemLogo : ImageView = view.findViewById(R.id.itemLogo)
            var itemPrice: TextView = view.findViewById(R.id.itemPrice)
        }
        @NonNull
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
            return MyViewHolder(itemView)
        }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = itemsList[position]
            holder.itemTextView.text = item.name_fr
            holder.itemPrice.text = item.prices[0].price + "€"
            holder.itemView.setOnClickListener {
                clickListener(item)
            }
            Picasso.get().load(item.images[0].ifEmpty { null })
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.itemLogo)
        }
        override fun getItemCount(): Int {
            return itemsList.size
        }
}