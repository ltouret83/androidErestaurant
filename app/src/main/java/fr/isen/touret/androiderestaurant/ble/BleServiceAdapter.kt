package fr.isen.touret.androiderestaurant.ble

import android.bluetooth.BluetoothGattCharacteristic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.isen.touret.androiderestaurant.R

class BleServiceAdapter(bleServices: MutableList<BleService>) :ExpandableRecyclerViewAdapter<BleServiceAdapter.ServiceViewHolder, BleServiceAdapter.CharacteristicViewHolder>(bleServices){

    class ServiceViewHolder(itemView: View): GroupViewHolder(itemView){
        var serviceName: TextView = itemView.findViewById(R.id.serviceName)
        var serviceUuid : TextView = itemView.findViewById(R.id.uuid)
        var serviceArrow : ImageView = itemView.findViewById(R.id.serviceArrow)

    }

    class CharacteristicViewHolder(itemView: View): ChildViewHolder(itemView){
        var characteristicName: TextView = itemView.findViewById(R.id.characteristicName)
        var characteristicUuid : TextView = itemView.findViewById(R.id.characteristicUuid)
        var butonRead : Button = itemView.findViewById(R.id.lectureAction)
        var butonEcrire : Button = itemView.findViewById(R.id.readAction)



    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ServiceViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.cell_ble_service,parent,false)
        return ServiceViewHolder(itemView)
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): CharacteristicViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.cell_ble_service,parent,false)
        return CharacteristicViewHolder(itemView)
    }

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val characteristic = group.items.get(childIndex) as BluetoothGattCharacteristic
        holder.characteristicName.text= characteristic.uuid.toString()
    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        holder.serviceName.text
    }

}
