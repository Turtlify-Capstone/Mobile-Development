package com.bangkit.turtlify.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Turtle(

    @ColumnInfo(name = "nama_lokal")
    val namaLokal: String? = null,

    @ColumnInfo(name = "persebaran_habitat")
    val persebaranHabitat: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "habitat")
    val habitat: String? = null,

    @ColumnInfo(name = "nama_latin")
    val namaLatin: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "latitude")
    val latitude: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "longitude")
    val longitude: String? = null,

    @ColumnInfo(name = "status_konversi")
    val statusKonversi: String? = null
) : Parcelable