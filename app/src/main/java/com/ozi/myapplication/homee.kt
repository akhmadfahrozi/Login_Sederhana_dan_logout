package com.ozi.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_homee.*

class homee : AppCompatActivity() {
    lateinit var p: CardView
    lateinit var shr: shared
    lateinit var tmb : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homee)
        tmb = findViewById(R.id.us)
        p = findViewById(R.id.logout)
        shr = shared(applicationContext)

        tmb.setOnClickListener {
            val pindah = Intent(this@homee,Upload::class.java)
            startActivity(pindah)
        }

        p.setOnClickListener {
            shr.clearUser()
            val pindah = Intent(this@homee, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(pindah)
        }


        val sharedPref: shared = shared(this)

        name1.setText(sharedPref.getValueString("name"))
        usernm.setText(sharedPref.getValueString("username"))


    }

}
