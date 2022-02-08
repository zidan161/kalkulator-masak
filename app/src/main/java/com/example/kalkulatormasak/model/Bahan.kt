package com.example.kalkulatormasak.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Bahan (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bahanId")
    var bahanId: Long = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "quantity")
    var qty: Float = 0f,

    @ColumnInfo(name = "unit")
    var unit: String? = null
): Parcelable
