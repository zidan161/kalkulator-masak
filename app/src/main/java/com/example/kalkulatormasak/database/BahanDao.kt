package com.example.kalkulatormasak.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kalkulatormasak.model.Bahan

@Dao
interface BahanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bahan: Bahan)

    @Update
    fun update(bahan: Bahan)

    @Delete
    fun delete(bahan: Bahan)

    @Query("SELECT * from bahan ORDER BY bahanId ASC")
    fun getAllBahan(): LiveData<List<Bahan>>

    @Query("SELECT * from bahan WHERE quantity > 0.0")
    fun getAvailableBahan(): LiveData<List<Bahan>>

    @Query("SELECT * from bahan WHERE bahanId = :id")
    fun getBahanById(id: Long): LiveData<Bahan>

    @Query("UPDATE bahan SET quantity = quantity + :qty WHERE bahanId = :id")
    fun increaseQty(id: Long, qty: Float)

    @Query("UPDATE bahan SET quantity = quantity - :qty WHERE bahanId = :id")
    fun decreaseQty(id: Long, qty: Float)

    @Query("UPDATE bahan SET quantity = 0")
    fun resetBahanHarian()
}