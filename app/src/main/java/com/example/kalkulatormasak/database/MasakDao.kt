package com.example.kalkulatormasak.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kalkulatormasak.model.Masak

@Dao
interface MasakDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(masak: Masak)

    @Update
    fun update(masak: Masak)

    @Delete
    fun delete(masak: Masak)

    @Query("SELECT * from masak ORDER BY id ASC")
    fun getAllMasak(): LiveData<List<Masak>>

    @Query("SELECT * from masak WHERE menuId = :menuId")
    fun getMasakByMenuId(menuId: Long): Masak?

    @Query("UPDATE masak SET count = count + 1 WHERE menuId = :menuId AND reportDate = :date")
    fun increaseMasakCount(menuId: Long, date: String)
}