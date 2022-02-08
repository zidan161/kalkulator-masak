package com.example.kalkulatormasak.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class BahanOnMenu(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "menuId")
    var menuId: Long = 0,

    @ColumnInfo(name = "bahanId")
    var bahanId: Long = 0,

    @ColumnInfo(name = "quantity")
    var qty: Float = 0f
): Parcelable
