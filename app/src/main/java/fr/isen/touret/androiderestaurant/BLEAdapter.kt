package fr.isen.touret.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.touret.androiderestaurant.databinding.ItemBleBinding

internal class BLEAdapter(private var itemsList: MutableList<ScanResult>) : RecyclerView.Adapter<BLEAdapter.MyViewHolder>() {
    private lateinit var binding: ItemBleBinding

    internal inner class MyViewHolder(binding: ItemBleBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.deviceView
        val distance = binding.distanceView
        val macAdress = binding.macView
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemBleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        if (item.device.name == null){
            holder.name.text = "Device Unknown"
        }
        else{
            holder.name.text = item.device.name
        }

        holder.distance.text = item.rssi.toString()
        holder.macAdress.text = item.device.address
    }
}