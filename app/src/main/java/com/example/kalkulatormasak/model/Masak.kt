package com.example.kalkulatormasak.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Masak (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "menuId")
    var menuId: Long? = null,

    @ColumnInfo(name = "menuName")
    var menuName: String? = null,

    @ColumnInfo(name = "reportDate")
    var reportDate: String? = null,

    @ColumnInfo(name = "count")
    var count: Int = 0
): Parcelable