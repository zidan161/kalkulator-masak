package com.example.kalkulatormasak.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kalkulatormasak.model.Report
import com.example.kalkulatormasak.model.ReportWithMasak

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(report: Report)

    @Update
    fun update(report: Report)

    @Delete
    fun delete(report: Report)

    @Query("SELECT * from report")
    fun getAllReport(): LiveData<List<Report>>

    @Query("SELECT EXISTS (SELECT 1 from report WHERE date = :date)")
    fun getExistReport(date: String): LiveData<Boolean?>

    @Transaction
    @Query("SELECT * from report")
    fun getAllReportWithMasak(): LiveData<List<ReportWithMasak>>
}