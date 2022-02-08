package com.example.kalkulatormasak.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Menu(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "menuId")
    var menuId: Long = 0,

    @ColumnInfo(name = "name")
    var name: String = "",
): Parcelable