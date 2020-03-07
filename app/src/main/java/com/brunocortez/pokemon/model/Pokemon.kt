package com.brunocortez.pokemon.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("number") val number: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageURL") val imageUrl: String
): Parcelable