package com.brunocortez.pokemon.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("number") val number: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageURL") val imageUrl: String,
    @SerializedName("attack") var attack: Int,
    @SerializedName("defense") var defense: Int,
    @SerializedName("ps") var ps: Int,
    @SerializedName("velocity") var velocity: Int,
    @SerializedName("description") val description: String
): Parcelable
