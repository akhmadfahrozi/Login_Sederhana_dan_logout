package com.ozi.myapplication.login

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
data class ResponseLogin(

    @field:SerializedName("data")
    val data: List<DataItem>? = null,
    val status: Int,
    val msg: String
)