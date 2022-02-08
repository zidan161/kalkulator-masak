package com.example.kalkulatormasak.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kalkulatormasak.model.BahanOnMenu

@Dao
interface BahanOnMenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bahanOnMenu: BahanOnMenu)

    @Update
    fun update(bahanOnMenu: BahanOnMenu)

    @Delete
    fun delete(bahanOnMenu: BahanOnMenu)

    @Query("SELECT * from bahanonmenu")
    fun getAllBahanOnMenu(): LiveData<List<BahanOnMenu>>

    @Query ("SELECT * FROM bahanonmenu WHERE menuId = :menuId")
    fun getAllBahanOnMenuById(menuId: Long): LiveData<List<BahanOnMenu>>

    @Query ("DELETE FROM bahanonmenu WHERE menuId = :menuId")
    fun deleteAllBahanOnMenu(menuId: Long)
}