package com.example.kalkulatormasak

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kalkulatormasak.database.*
import com.example.kalkulatormasak.model.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainRepository(app: Application) {

    private val bahanDao: BahanDao
    private val menuDao: MenuDao
    private val bahanOnMenuDao: BahanOnMenuDao
    private val masakDao: MasakDao
    private val reportDao: ReportDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MasakDatabase.getDatabase(app)
        bahanDao = db.bahanDao()
        menuDao = db.menuDao()
        bahanOnMenuDao = db.menuBahanDao()
        masakDao = db.masakDao()
        reportDao = db.reportDao()
    }

    //CRUD Bahan
    fun getAllBahan(): LiveData<List<Bahan>> = bahanDao.getAllBahan()

    fun getAvailableBahan(): LiveData<List<Bahan>> = bahanDao.getAvailableBahan()

    fun getBahanById(id: Long): LiveData<Bahan> = bahanDao.getBahanById(id)

    fun insertBahan(bahan: Bahan) {
        executorService.execute { bahanDao.insert(bahan) }
    }

    fun updateBahan(bahan: Bahan) {
        executorService.execute { bahanDao.update(bahan) }
    }

    fun deleteBahan(bahan: Bahan) {
        executorService.execute { bahanDao.delete(bahan) }
    }

    fun increaseQty(id: Long, qty: Float) {
        executorService.execute { bahanDao.increaseQty(id, qty) }
    }

    fun decreaseQty(id: Long, qty: Float) {
        executorService.execute { bahanDao.decreaseQty(id, qty) }
    }

    fun resetBahan() {
        executorService.execute { bahanDao.resetBahanHarian() }
    }

    //CRUD Menu
    fun getAllMenus(): LiveData<List<Menu>> = menuDao.getAllMenus()

    fun getMenuByName(name: String): LiveData<Menu> = menuDao.getMenuByName(name)

    fun getMenuWithBahanById(menuId: Long): LiveData<MenuWithBahan> = menuDao.getAllBahanOnMenuById(menuId)

    fun getMenuWithBahan(): LiveData<List<MenuWithBahan>> = menuDao.getAllBahanOnMenu()

    fun insertMenu(menu: Menu) {
        executorService.execute { menuDao.insert(menu) }
    }

    fun updateMenu(menu: Menu) {
        executorService.execute { menuDao.update(menu) }
    }

    fun deleteMenu(menu: Menu) {
        executorService.execute { menuDao.delete(menu) }
    }

    //CRUD BahanOnMenu
    fun getAllBahanOnMenu(): LiveData<List<BahanOnMenu>> = bahanOnMenuDao.getAllBahanOnMenu()

    fun getAllBahanOnMenuById(menuId: Long): LiveData<List<BahanOnMenu>> = bahanOnMenuDao.getAllBahanOnMenuById(menuId)

    fun insertBahanOnMenu(bahanOnMenu: BahanOnMenu) {
        executorService.execute { bahanOnMenuDao.insert(bahanOnMenu) }
    }

    fun updateBahanOnMenu(bahanOnMenu: BahanOnMenu) {
        executorService.execute { bahanOnMenuDao.update(bahanOnMenu) }
    }

    fun deleteBahanOnMenu(bahanOnMenu: BahanOnMenu) {
        executorService.execute { bahanOnMenuDao.delete(bahanOnMenu) }
    }

    fun updateAllBahanOnMenu(menuId: Long, data: List<BahanOnMenu>) {
        executorService.execute {
            bahanOnMenuDao.deleteAllBahanOnMenu(menuId)
            insertAllBahanOnMenu(menuId, data)
        }
    }

    fun insertAllBahanOnMenu(menuId: Long, data: List<BahanOnMenu>) {
        executorService.execute {
            for (i in data) {
                i.menuId = menuId
                bahanOnMenuDao.insert(i)
            }
        }
    }

    //CRUD Masak
    fun getAllMasak(): LiveData<List<Masak>> = masakDao.getAllMasak()

    fun updateOrInsertMasak(newMasak: Masak) {
        executorService.execute {
            val masak = masakDao.getMasakByMenuId(newMasak.menuId!!)

            if (masak == null) {
                masakDao.insert(newMasak)
            } else {
                masakDao.increaseMasakCount(newMasak.menuId!!, newMasak.reportDate!!)
            }
        }
    }

    fun insertMasak(masak: Masak) {
        executorService.execute { masakDao.insert(masak) }
    }

    fun updateMasak(masak: Masak) {
        executorService.execute { masakDao.update(masak) }
    }

    fun deleteMasak(masak: Masak) {
        executorService.execute { masakDao.delete(masak) }
    }

    fun increaseMasakCount(menuId: Long, date: String) {
        executorService.execute { masakDao.increaseMasakCount(menuId, date) }
    }

    //CRUD Report
    fun getAllReport(): LiveData<List<Report>> = reportDao.getAllReport()

    fun getAllReportWithMasak(): LiveData<List<ReportWithMasak>> = reportDao.getAllReportWithMasak()

    fun getExistReport(date: String): LiveData<Boolean?> = reportDao.getExistReport(date)

    fun insertReport(report: Report) {
        executorService.execute { reportDao.insert(report) }
    }

    fun updateReport(report: Report) {
        executorService.execute { reportDao.update(report) }
    }

    fun deleteReport(report: Report) {
        executorService.execute { reportDao.delete(report) }
    }
}