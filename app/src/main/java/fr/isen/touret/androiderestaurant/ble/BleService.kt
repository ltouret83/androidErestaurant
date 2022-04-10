package fr.isen.touret.androiderestaurant.ble

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class BleService(val name: String, val characteritics: MutableList<BluetoothGattCharacteristic>):
    ExpandableGroup<BluetoothGattCharacteristic>(name, characteritics){

}