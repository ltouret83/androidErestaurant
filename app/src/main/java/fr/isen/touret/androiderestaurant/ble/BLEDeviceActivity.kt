package fr.isen.touret.androiderestaurant.ble

import android.annotation.SuppressLint
import android.bluetooth.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.touret.androiderestaurant.databinding.ActivityBledeviceBinding
import fr.isen.touret.androiderestaurant.R

class BLEDeviceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBledeviceBinding
    private var bluetoothGatt: BluetoothGatt? = null


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBledeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device= intent.getParcelableExtra<BluetoothDevice?>(BLEScanActivity.DEVICE_KEY)

        binding.deviceName.text = device?.name ?: "Nom inconnu"
        binding.deviceStatu.text = getString(R.string.ble_device_disconnected)

        connectToDevice(device)
    }

    @SuppressLint("MissingPermission")
    private fun connectToDevice(device: BluetoothDevice?) {
       bluetoothGatt = device?.connectGatt(this, true, object: BluetoothGattCallback(){
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                ConnectionStateChange(gatt,newState)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                val bleServices = gatt?.services?.map{ BleService(it.uuid.toString(),it.characteristics) } ?: arrayListOf()
                val adapter = BleServiceAdapter(bleServices as MutableList<BleService>)
                runOnUiThread {
                    binding.serviceList.layoutManager = LinearLayoutManager(this@BLEDeviceActivity)
                    binding.serviceList.adapter = adapter
                }
            }

            override fun onDescriptorRead(
                gatt: BluetoothGatt?,
                descriptor: BluetoothGattDescriptor?,
                status: Int
            ) {
                super.onDescriptorRead(gatt, descriptor, status)
            }
        })
        bluetoothGatt?.connect()
    }

    @SuppressLint("MissingPermission")
    private fun ConnectionStateChange(gatt: BluetoothGatt?, newState: Int) {
        val state = if(newState == BluetoothProfile.STATE_CONNECTED){
            gatt?.discoverServices()
            getString(R.string.ble_device_connected)
        }else{
            getString(R.string.ble_device_disconnected)
        }
        runOnUiThread{
            binding.deviceStatu.text = state

        }

    }

    override fun onStop() {
        super.onStop()
        closeBluetoothGatt()
    }

    @SuppressLint("MissingPermission")
    private fun closeBluetoothGatt() {
        bluetoothGatt?.close()
        bluetoothGatt = null
    }

}