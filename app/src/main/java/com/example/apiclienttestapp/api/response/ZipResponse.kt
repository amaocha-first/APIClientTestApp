package com.example.apiclienttestapp.api.response

import com.example.apiclienttestapp.data.AddressEntity

data class ZipResponse(
    var message: String? = null,
    var status: Int? = null,
    var results: ArrayList<AddressEntity> = ArrayList()
)
