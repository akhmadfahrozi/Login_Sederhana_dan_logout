package com.ozi.myapplication.Network

import com.ozi.myapplication.login.ResponseLogin
import com.ozi.myapplication.upload.ResponseUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("login_json")

    //value harus sama persis yang ada di get (controller), username:Srting
    //parameter : menampung yg akan dikirim
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
        //call alt+enter = call retrofit 2
    ): Call<ResponseLogin>


    @Multipart
    @POST("upload_file")
    fun uploadfile(
        @Part file: MultipartBody.Part,
        @Part("nama_pan") nama_pan: RequestBody,
        @Part("Kabupaten_atau_kota") Kabupaten_atau_kota: RequestBody,
        @Part("Kecamatan") Kecamatan: RequestBody,
        @Part("Jabatan") Jabatan: String,
        @Part("NIK") NIK: RequestBody,
        @Part("Tanggal_Lahir") Tanggal_Lahir: RequestBody,
        @Part("Divisi") Divisi: RequestBody,
        @Part("Tempat_Lahir") Tempat_Lahir: RequestBody,
        @Part("Jenis_Kelamin") Jenis_Kelamin: String?,
        @Part("Agama") Agama: RequestBody,
        @Part("Status_perkawinan") Status_perkawinan: String?,
        @Part("alamat") alamat: RequestBody,
        @Part("Email") Email: RequestBody,
        @Part("pendidikan") pendidikan: RequestBody,
        @Part("pekerjaan_sebelumnya") pekerjaan_sebelumnya: RequestBody,
        @Part("Tanggal_Pengangkatan") Tanggal_Pengangkatan: RequestBody,
        @Part("NO_sk") No_sk: RequestBody,
        @Part("Penggalaman_Kepemiluan") Penggalaman_Kepemiluan: RequestBody


    ): Call<ResponseUpload>
}