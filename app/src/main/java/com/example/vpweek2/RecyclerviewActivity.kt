package com.example.vpweek2

import Adapter.ListDataRVAdapter
import Database.GlobalVar
import Interface.CardListener
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import Ayam
import Sapi
import Kambing
import com.example.vpweek2.databinding.ActivityRecyclerviewBinding

class RecyclerviewActivity : AppCompatActivity(), CardListener {

    //Rifqie Tilqa Reamizard -- 0706012110025

    private lateinit var vBind: ActivityRecyclerviewBinding
    private val adapter = ListDataRVAdapter(GlobalVar.listDataMovie, this)
    private val ubahFilter = ListDataRVAdapter(GlobalVar.tempDataMovie, this)

    private var list: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(vBind.root)
        supportActionBar?.hide()
        permission()
        setupRecyclerView()
        listener()
    }

    override fun onResume() {
        super.onResume()
        if(GlobalVar.tipeFilter == "Ayam"){
            GlobalVar.tempDataMovie.clear()
            for(i in 0..GlobalVar.listDataMovie.size-1){
                if(GlobalVar.listDataMovie[i] is Ayam){
                    GlobalVar.tempDataMovie.add(GlobalVar.listDataMovie[i])
                }
            }
            vBind.listDataRV.adapter = ubahFilter
            adapter.notifyDataSetChanged()
        }else if(GlobalVar.tipeFilter == "Sapi"){
            GlobalVar.tempDataMovie.clear()
            for(i in 0..GlobalVar.listDataMovie.size-1){
                if(GlobalVar.listDataMovie[i] is Sapi){
                    GlobalVar.tempDataMovie.add(GlobalVar.listDataMovie[i])
                }
            }
            vBind.listDataRV.adapter = ubahFilter
            adapter.notifyDataSetChanged()
        }else if(GlobalVar.tipeFilter == "Kambing"){
            GlobalVar.tempDataMovie.clear()
            for(i in 0..GlobalVar.listDataMovie.size-1){
                if(GlobalVar.listDataMovie[i] is Kambing){
                    GlobalVar.tempDataMovie.add(GlobalVar.listDataMovie[i])
                }
            }
            vBind.listDataRV.adapter = ubahFilter
            adapter.notifyDataSetChanged()
        }else{
            adapter.notifyDataSetChanged()
        }
        list = GlobalVar.listDataMovie.size
        if (list == 0) {
            vBind.addMovie.alpha = 1f
        } else {
            vBind.addMovie.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }

    private fun listener() {
        vBind.addDataFAB.setOnClickListener {
            val myIntent = Intent(this, AddEditMovie::class.java)
            startActivity(myIntent)
        }
        vBind.btnAyam.setOnClickListener {
            GlobalVar.tipeFilter = "Ayam"
            GlobalVar.tempDataMovie.clear()
            for(i in 0 until GlobalVar.listDataMovie.size-1){
                if(GlobalVar.listDataMovie[i] is Ayam){
                    GlobalVar.tempDataMovie.add(GlobalVar.listDataMovie[i])
                }
            }
            vBind.listDataRV.adapter = ubahFilter
            adapter.notifyDataSetChanged()
        }

        vBind.btnSapi.setOnClickListener {
            GlobalVar.tipeFilter = "Sapi"
            GlobalVar.tempDataMovie.clear()
            for(i in 0 until GlobalVar.listDataMovie.size-1){
                if(GlobalVar.listDataMovie[i] is Sapi){
                    GlobalVar.tempDataMovie.add(GlobalVar.listDataMovie[i])
                }
            }
            vBind.listDataRV.adapter = ubahFilter
            adapter.notifyDataSetChanged()
        }

        vBind.btnKambing.setOnClickListener {
            GlobalVar.tipeFilter = "Kambing"
            GlobalVar.tempDataMovie.clear()
            for(i in 0 until GlobalVar.listDataMovie.size-1){
                if(GlobalVar.listDataMovie[i] is Kambing){
                    GlobalVar.tempDataMovie.add(GlobalVar.listDataMovie[i])
                }
            }
            vBind.listDataRV.adapter = ubahFilter
            adapter.notifyDataSetChanged()
        }
        vBind.btnSemua.setOnClickListener {
            GlobalVar.tipeFilter = ""
            vBind.listDataRV.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                GlobalVar.STORAGE_PERMISSION_CODE
            )
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GlobalVar.STORAGE_PERMISSION_CODE
            )
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GlobalVar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        vBind.listDataRV.layoutManager = layoutManager   // Set layout
        vBind.listDataRV.adapter = adapter   // Set adapter
    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, AddEditMovie::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myIntent)
    }
}