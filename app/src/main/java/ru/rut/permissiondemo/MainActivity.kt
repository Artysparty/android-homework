 package ru.rut.permissiondemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rut.permissiondemo.common.Common
import ru.rut.permissiondemo.inter.RetrofitService
import ru.rut.permissiondemo.model.Character
import ru.rut.permissiondemo.util.requestPermissionCompat
import ru.rut.permissiondemo.util.showSnackbar

 const val PERMISSION_REQUEST = 1

 class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {


     private lateinit var retService: RetrofitService
     lateinit var characterAdapter: CharacterAdapter
     lateinit var layouManager: LinearLayoutManager
     private lateinit var characterRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = Common.retrofitService
        characterRecyclerView = findViewById(R.id.content)
        layouManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        characterRecyclerView.layoutManager = layouManager

        requestData(21)
    }

     private fun requestData(_pageNumber: Int) {
         var pageNumber = _pageNumber
         val characters: MutableList<Character> = mutableListOf()
         for ( i in 1..3 ) {
             retService.getCharactersList(pageNumber, 50)
                 .enqueue(object : Callback<MutableList<Character>> {
                     override fun onResponse(
                         call: Call<MutableList<Character>>,
                         response: Response<MutableList<Character>>
                     ) {
                         characters.addAll(response.body()!!.toList())
                         pageNumber++
                         characterAdapter =
                             CharacterAdapter(this@MainActivity, characters)
                         characterRecyclerView.adapter = characterAdapter
                         characterAdapter.notifyDataSetChanged()
                     }

                     override fun onFailure(call: Call<MutableList<Character>>, t: Throwable) {}
                 })
         }
     }
}