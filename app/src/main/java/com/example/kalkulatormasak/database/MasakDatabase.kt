package com.example.kalkulatormasak.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kalkulatormasak.model.*

@Database(entities = [Menu::class, Bahan::class,
    BahanOnMenu::class, Masak::class, Report::class], version = 1)
abstract class MasakDatabase : RoomDatabase() {

    abstract fun bahanDao(): BahanDao
    abstract fun menuDao(): MenuDao
    abstract fun menuBahanDao(): BahanOnMenuDao
    abstract fun masakDao(): MasakDao
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: MasakDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MasakDatabase {
            if (INSTANCE == null) {
                synchronized(MasakDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MasakDatabase::class.java, "masak_db"
                    ).build()
                }
            }
            return INSTANCE as MasakDatabase
        }
    }
}