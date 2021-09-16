package com.gogolook.homework.model.api.model.response


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("hits")
    val hits: ArrayList<ImageInfo>
)