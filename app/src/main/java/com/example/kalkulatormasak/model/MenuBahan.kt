package com.example.kalkulatormasak.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuWithBahan(
    @Embedded
    val menu: Menu,

    @Relation(
        entity = BahanOnMenu::class,
        parentColumn = "menuId",
        entityColumn = "menuId"
    )
    val listBahan: List<BahanWithRealBahan>
): Parcelable

@Parcelize
data class BahanWithRealBahan(
    @Embedded
    val bahan: BahanOnMenu,

    @Relation(
        parentColumn = "bahanId",
        entityColumn = "bahanId"
    )
    val realBahan: Bahan
): Parcelable