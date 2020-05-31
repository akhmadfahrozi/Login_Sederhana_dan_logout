package com.ozi.myapplication

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ozi.myapplication.Network.NewtworkClass
import com.ozi.myapplication.upload.ResponseUpload
import kotlinx.android.synthetic.main.activity_hello.*
import kotlinx.android.synthetic.main.activity_hello.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


class Upload : hello() {
    private var Jabatan = ""
    private var Status_perkawinan = ""
    private var Jenis_Kelamin = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        date1.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay ->
                hasil.setText("" + mDay + "/" + mMonth + "/" + mYear)},
                year,month,day)

            dpd.show()
        }
        date2.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay ->
                hasil1.setText("" + mDay + "/" + mMonth + "/" + mYear)},
                year,month,day)

            dpd.show()
        }

        upload_file.setOnClickListener {
            if (flagUpload)
                doUploadFile()
            else
                Toast.makeText(this, "Anda belum memiliki berkas", Toast.LENGTH_SHORT).show()
        }
        radio.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.Ketua -> {
                    Jabatan = "Ketua"
                }
                R.id.Anggota -> {
                    Jabatan = "Anggota"
                }
            }
        }
        radio1.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.Laki -> {
                    Jenis_Kelamin = "Laki"
                }
                R.id.Perempuan -> {
                    Jenis_Kelamin = "Perempuan"
                }
            }
        }
        radio2.setOnCheckedChangeListener { group, checkedid ->
            when (checkedid) {
                R.id.Belum -> {
                    Status_perkawinan = "Belum Kawin"
                }
                R.id.Kawin -> {
                    Status_perkawinan = "Kawin"
                }
                R.id.cerai -> {
                    Status_perkawinan = "Cerai"
                }
            }
        }


        filePicker.setOnClickListener {
            openFilePicker()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILEPICKERREQ && resultCode == Activity.RESULT_OK) {
            data?.data?.let { getMetaData(it, imagePreview) }
            flagUpload = true
        }
    }

    private fun decodeImageToByte(): ByteArray {
        val bmp = (imagePreview.drawable as BitmapDrawable).bitmap

        //buat object baru untuk
        val bs = ByteArrayOutputStream()

        //convert draweble dari image view ke jpg dengan quality 100
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bs)
        val imageInBye = bs.toByteArray()
        return imageInBye
    }

    private fun doUploadFile() {
        val files = RequestBody.create(MediaType.parse("image/*"), decodeImageToByte())
        val imageInput = MultipartBody.Part.createFormData(
            "file", "${System.nanoTime()}.jpg",
            files
        )

        val nama_pan = RequestBody.create(MediaType.parse("text/plain"), nama.text.toString())
        val Kabupaten_atau_kota =
            RequestBody.create(MediaType.parse("text/plain"), kota.selectedItem.toString())
        val Kecamatan = RequestBody.create(MediaType.parse("text/plain"), kecsmstsn.text.toString())
        val NIK = RequestBody.create(MediaType.parse("text/plain"), nik.text.toString())
        val Tanggal_Lahir = RequestBody.create(MediaType.parse("text/plain"), hasil.text.toString())
        val Divisi = RequestBody.create(MediaType.parse("text/plain"), divisi.selectedItem.toString())
        val Tempat_Lahir = RequestBody.create(MediaType.parse("text/plain"), lahir.text.toString())
        val Agama = RequestBody.create(MediaType.parse("text/plain"), agama.selectedItem.toString())
        val Email = RequestBody.create(MediaType.parse("text/plain"), email.text.toString())
        val NO_sk = RequestBody.create(MediaType.parse("text/plain"), sk.text.toString())
        val pekerjaan_sebelumnya = RequestBody.create(MediaType.parse("text/plain"), pekerjaan.selectedItem.toString())
        val Tanggal_Pengangkatan = RequestBody.create(MediaType.parse("text/plain"), hasil1.text.toString())
        val Penggalaman_Kepemiluan = RequestBody.create(MediaType.parse("text/plain"), pengalaman.text.toString())
        val pendidikan = RequestBody.create(MediaType.parse("text/plain"), pendidikan.selectedItem.toString())
        val alamat = RequestBody.create(MediaType.parse("text/plain"), almt.text.toString())


        NewtworkClass.getService()
            .uploadfile(
                imageInput,
                nama_pan,
                Kabupaten_atau_kota,
                Kecamatan,
                Jabatan,
                NIK,
                Tanggal_Lahir,
                Divisi,
                Tempat_Lahir,
                Jenis_Kelamin,
                Agama,
                Status_perkawinan,
                alamat,
                Email,
                pendidikan,
                pekerjaan_sebelumnya,
                Tanggal_Pengangkatan,
                NO_sk,
                Penggalaman_Kepemiluan

                )
            .enqueue(object : Callback<ResponseUpload> {
                override fun onFailure(call: Call<ResponseUpload>, t: Throwable) {
                    Toast.makeText(this@Upload, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onResponse(
                    call: Call<ResponseUpload>,
                    response: Response<ResponseUpload>
                ) {
                    if (response.isSuccessful) {
                        val code = response.body()?.code
                        if (code == 200) {
                            Toast.makeText(
                                this@Upload,
                                response.body()?.message.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        } else {
                            Toast.makeText(
                                this@Upload,
                                response.body()?.message.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    } else {
                        Toast.makeText(
                            this@Upload,
                            response.errorBody()?.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }


            })
    }
}