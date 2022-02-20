package com.example.gallerydocs

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gallerydocs.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val result: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (permission) {
                Log.e("True permission", "=============")
            } else {
                Log.e("False permission", "=============")
            }
        }

    val openGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            //
        }
    }

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.img.setImageURI(it)
    }
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {

    }
    private val camPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            Log.e("TAG", "GRANTED")
        } else {
            Toast.makeText(applicationContext, "Permission is not Granted", Toast.LENGTH_SHORT).show()
        }
    }

    private var isRequestGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkCameraPermission()) result.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        setOnClick()
    }

    private fun camPermissionGranted(): Boolean = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    private fun checkCameraPermission(): Boolean {
//        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun setOnClick() {
        result.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.btnImage.setOnClickListener {
            selectImage.launch("application/pdf")
        }
    }

//    private fun check(): Boolean {
//        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            //            result.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//            true
//        } else {
//            result.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//            false
//        }
//    }
}