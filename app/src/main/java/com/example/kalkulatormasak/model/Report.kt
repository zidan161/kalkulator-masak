package com.example.kalkulatormasak.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Report(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "admin")
    var admin: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
): Parcelable

@Parcelize
data class ReportWithMasak(
    @Embedded
    var report: Report,

    @Relation(
        parentColumn = "date",
        entityColumn = "reportDate"
    )
    var masak: List<Masak>
): Parcelable