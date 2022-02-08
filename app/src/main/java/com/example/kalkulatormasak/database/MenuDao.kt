package com.example.kalkulatormasak.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kalkulatormasak.model.BahanOnMenu
import com.example.kalkulatormasak.model.Menu
import com.example.kalkulatormasak.model.MenuWithBahan

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(menu: Menu)

    @Update
    fun update(menu: Menu)

    @Delete
    fun delete(menu: Menu)

    @Query("SELECT * from menu ORDER BY menuId ASC")
    fun getAllMenus(): LiveData<List<Menu>>

    @Query("SELECT * from menu WHERE name = :name")
    fun getMenuByName(name: String): LiveData<Menu>

    @Transaction
    @Query("SELECT * from menu")
    fun getAllBahanOnMenu(): LiveData<List<MenuWithBahan>>

    @Transaction
    @Query("SELECT * from menu WHERE menuId = :menuId")
    fun getAllBahanOnMenuById(menuId: Long): LiveData<MenuWithBahan>
}