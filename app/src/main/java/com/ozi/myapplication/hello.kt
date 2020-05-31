package com.ozi.myapplication

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

open class hello : AppCompatActivity() {

    var fileUri: Uri? = null
    var realPath : String? = null
    val FILEPICKERREQ = 1024
    var flagUpload = false
    fun openFilePicker(){
        val filePicker = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //TAMBAHKAN kategori untuk menanmpilkan apikasi yang dapat membuka dot/gambar
        filePicker.addCategory(Intent.CATEGORY_OPENABLE)
        //SET TYPE FILE YANG AKAN DI BUKA PADA FILE MANAGER//FILE OPENER
        //     = */* BERARTI SEMUA FILE AKAN DITAMPILKAN
        filePicker.type = "*/*"
        //jalankan activity pada aplikasi
        startActivityForResult(Intent.createChooser(filePicker,"pilih file..."),FILEPICKERREQ)
    }
    fun getMetaData(uri: Uri,imageView: ImageView){
        // membuka file berulang kali
        val cursor: Cursor?= contentResolver.query(uri,
            null,
            null,
            null,
            null)

        if(cursor?.moveToFirst()==true){
            imageView.setImageURI(uri)

        }else {
            //tampilan ketika error
            Toast.makeText(this,"Uri tidak dikenal",Toast.LENGTH_SHORT).show()
        }

    }
}
