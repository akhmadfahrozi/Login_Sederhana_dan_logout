package com.ozi.myapplication

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ozi.myapplication.Network.NewtworkClass
import com.ozi.myapplication.login.ResponseLogin
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.des.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var tv: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val masuk = findViewById(R.id.login) as Button
//        tv = findViewById(R.id.textView)
//
//        tv.setOnClickListener {
//            val alertadd = AlertDialog.Builder(this@MainActivity)
//            val factory = LayoutInflater.from(this@MainActivity)
//            val view = factory.inflate(R.layout.des, null)
//
//
//            alertadd.setView(view)
//            alertadd.setNeutralButton(
//                "Here!"
//            ) { dlg, sumthin -> }
//
//            alertadd.show()
//        }

        masuk.setOnClickListener {
            masuk(name.text.toString(), password.text.toString())

            masuk.requestFocus()
        }

        val sharedPreference: shared = shared(this)
        if (sharedPreference.getValueBoolean("status")) {
            startActivity(Intent(this, homee::class.java))
            finish()
        }
    }

    private fun masuk(username: String, password: String) {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Login ....")
        progressdialog.setCancelable(false)
        progressdialog.show()

        val shared: shared = shared(this)
        val apiService = NewtworkClass.getService()
        val requestCall = apiService.login(username, password)



        NewtworkClass.getService().login(username, password)
            .enqueue(object : Callback<ResponseLogin> {
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                }


                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    if (response.isSuccessful) {
                        var data = response.body()?.data
                        data?.forEach {
                            shared.save("id_user", it.idUser!!)
                            shared.save("name", it.name!!)
                            shared.save("username", it.username!!)
                            shared.save("status", true)



                            startActivity(
                                Intent(this@MainActivity, homee::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            )

                            Toast.makeText(applicationContext, "Berhasil Login", Toast.LENGTH_SHORT)
                                .show()


//                            val pindah = Intent(this@MainActivity, Upload::class.java)
//                            startActivity(pindah)
//                            Toast.makeText(this@LoginActivity, it.nama, Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Log.d("server", response.body()!!.msg)
                        Toast.makeText(
                            applicationContext,
                            "Username atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressdialog.hide()


                    }

                }

            })

    }
}

