package fr.isen.touret.androiderestaurant.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.touret.androiderestaurant.R
import fr.isen.touret.androiderestaurant.databinding.ActivityBlescanBinding

class BLEScanActivity : AppCompatActivity() {
    private val itemList = ArrayList<ScanResult>()
    private lateinit var bleAdapter: BLEAdapter



    private lateinit var binding: ActivityBlescanBinding

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private var isScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlescanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recycler view

        val recyclerBle: RecyclerView = binding.itemBle
        bleAdapter = BLEAdapter(itemList) {
            val intent = Intent(this, BLEDeviceActivity::class.java)
            intent.putExtra("device", it)
            startActivity(intent)
        }


        when{
            bluetoothAdapter?.isEnabled == true -> {
                binding.logoStart.setOnClickListener {
                    startLeScanBLEWithPermission(!isScanning)
                }
                binding.textView.setOnClickListener {
                    startLeScanBLEWithPermission(!isScanning)
                }
            }
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayNoBLEUnAvailable()
            }
        }
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerBle.layoutManager = layoutManager
        recyclerBle.adapter = bleAdapter

        title = "AndroidToolBox"
    }
    override fun onStop(){
        super.onStop()
        startLeScanBLEWithPermission(false)
    }

    private fun startLeScanBLEWithPermission(enable: Boolean){
        if (checkAllPermissionGranted()) {
            startLeScanBLE(enable)
        }else{
            ActivityCompat.requestPermissions(this, getAllPermissions() ,
                ALL_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun checkAllPermissionGranted(): Boolean {
        return getAllPermissions().all { permission ->
            ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getAllPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if(enable) {
                isScanning =true
                startScan(scanCallback)
            }else{
                isScanning = false
                stopScan(scanCallback)
            }
            handlePlayStopAction()
        }
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("BLEScanActivity", "result: ${result.device.address}, rssi : ${result.rssi}")
            //addToList(result)
            bleAdapter.apply{
                addToList(result)
                notifyDataSetChanged()
            }
        }
    }


    private fun askBluetoothPermission(){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                startActivityForResult(enableBtIntent, ENABLE_PERMISSION_REQUEST_CODE)
            }

    }
    private fun displayNoBLEUnAvailable(){
        binding.logoStart.setImageResource(R.drawable.play)
        binding.textView.text = getString(R.string.ble_scan_error)
        binding.chargementBar.isIndeterminate = false
    }

    private fun handlePlayStopAction() {
        if(isScanning){
            binding.logoStart.setImageResource(R.drawable.stop)
            binding.textView.text = getString(R.string.ble_scan_stop)
            binding.chargementBar.isIndeterminate = true
        }
        else{
            binding.logoStart.setImageResource(R.drawable.play)
            binding.textView.text = getString(R.string.ble_scan_play)
            binding.chargementBar.isIndeterminate = false
        }
    }

    companion object {

        private const val ALL_PERMISSION_REQUEST_CODE= 100
        private const val ENABLE_PERMISSION_REQUEST_CODE= 1
        const val DEVICE_KEY = "device"



    }
    private fun addToList(result:ScanResult){
        val index:Int = itemList.indexOfFirst{ it.device.address==result.device.address }
        if(index == -1){
            itemList.add(result)
        }else{
            itemList[index]=result
        }
        itemList.sortBy { kotlin.math.abs(it.rssi) }
        bleAdapter.notifyDataSetChanged()
    }
}