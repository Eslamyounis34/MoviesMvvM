package com.example.kotlinstudy.ui

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlinstudy.R
import com.squareup.picasso.Picasso
import io.github.kobakei.materialfabspeeddial.FabSpeedDial
import okhttp3.internal.Version

class MovieImagePreview : AppCompatActivity() {
    val STORAGE_PERMISSION=100

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_image_preview)

        var fullimage:ImageView
        var imagefac:FabSpeedDial

        fullimage=findViewById(R.id.fullscreenimage)
        imagefac=findViewById(R.id.imagefabmenu)
        var intent=intent
        var imagePath= intent.extras?.getString("ImagePath")
        var imagefullpath="https://image.tmdb.org/t/p/w1280/"+imagePath

        Picasso.get().load("https://image.tmdb.org/t/p/w1280/"+imagePath).into(fullimage)



        imagefac.addOnMenuItemClickListener { fab, textView, itemId ->

           // downloadFile("Test", "File Desc", imagefullpath)
            checkForPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"Storage",STORAGE_PERMISSION)
           // Toast.makeText(applicationContext,"Done", Toast.LENGTH_SHORT).show()
        }
    }


    private fun downloadFile(fileName : String, url : String){



        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        val downloadManager= getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {

        fun innerChecker(name:String){
            if(grantResults.isEmpty()||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Permission Denied",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,"Permission Granted",Toast.LENGTH_SHORT).show()
                var intent=intent
                var imagePath= intent.extras?.getString("ImagePath")
                var imagefullpath="https://image.tmdb.org/t/p/w1280/"+imagePath

                downloadFile(imagePath.toString(),imagefullpath)

            }
        }
        when(requestCode){
          STORAGE_PERMISSION ->innerChecker("Storage")
        }
    }
    private fun checkForPermission(permission:String , name:String,requestCode: Int)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            when{
                ContextCompat.checkSelfPermission(applicationContext,permission)==PackageManager.PERMISSION_GRANTED ->{

                    var intent=intent
                    var imagePath= intent.extras?.getString("ImagePath")
                    var imagefullpath="https://image.tmdb.org/t/p/w1280/"+imagePath

                    downloadFile(imagePath.toString(), imagefullpath)
                    // Toast.makeText(applicationContext,"Permission Accepted",Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission)->showDialog(permission,name,requestCode)

                else ->ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
            }
        }

    }

    
    private fun showDialog(permission: String,name: String,requestCode: Int)
    {
       val builder=AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required")
                setTitle("Permission Required")
            setPositiveButton("Ok"){
                dialog, which ->
                ActivityCompat.requestPermissions(this@MovieImagePreview, arrayOf(permission),requestCode)
            }

        }
        val dialog=builder.create()
        dialog.show()
    }
}