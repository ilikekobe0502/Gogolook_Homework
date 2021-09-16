package com.gogolook.homework.model.api.model.response

import com.google.gson.annotations.SerializedName

data class ImageInfo(
    @SerializedName("id")
    var id: Int,
    @SerializedName("pageURL")
    var pageURL: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("tags")
    var tags: String,
    @SerializedName("previewURL")
    var previewURL: String,
    @SerializedName("previewWidth")
    var previewWidth: Int,
    @SerializedName("previewHeight")
    var previewHeight: Int,
    @SerializedName("webformatURL")
    var webFormatURL: String,
    @SerializedName("webformatWidth")
    var webFormatWidth: Int,
    @SerializedName("webformatHeight")
    var webFormatHeight: Int,
    @SerializedName("largeImageURL")
    var largeImageURL: String,
    @SerializedName("fullHDURL")
    var fullHDURL: String,
    @SerializedName("imageURL")
    var imageURL: String,
    @SerializedName("imageWidth")
    var imageWidth: Int,
    @SerializedName("imageHeight")
    var imageHeight: Int,
    @SerializedName("imageSize")
    var imageSize: Long,
    @SerializedName("views")
    var views: Long,
    @SerializedName("downloads")
    var downloads: Long,
    @SerializedName("likes")
    var likes: Long,
    @SerializedName("comments")
    var comments: Long,
    @SerializedName("user_id")
    var userId: Long,
    @SerializedName("user")
    var user: String,
    @SerializedName("userImageURL")
    var userImageURL: String,

)