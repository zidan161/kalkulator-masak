package com.example.kalkulatormasak

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kalkulatormasak.model.*

class MainViewModel(app: Application): ViewModel() {

    private val repository = MainRepository(app)

    //CRUD Menu
    fun getAllMenu(): LiveData<List<Menu>> = repository.getAllMenus()

    fun getMenuByName(name: String): LiveData<Menu> = repository.getMenuByName(name)

    fun getMenuWithBahan(): LiveData<List<MenuWithBahan>> = repository.getMenuWithBahan()

    fun getMenuWithBahanById(menuId: Long): LiveData<MenuWithBahan> = repository.getMenuWithBahanById(menuId)

    fun insertMenu(menu: Menu) = repository.insertMenu(menu)

    fun updateMenu(menu: Menu) = repository.updateMenu(menu)

    fun deleteMenu(menu: Menu) = repository.deleteMenu(menu)

    fun increaseQty(id: Long, qty: Float) = repository.increaseQty(id, qty)

    fun decreaseQty(id: Long, qty: Float) = repository.decreaseQty(id, qty)

    fun resetBahan() = repository.resetBahan()

    //CRUD Bahan
    fun getAllBahan(): LiveData<List<Bahan>> = repository.getAllBahan()

    fun getBahanById(id: Long): LiveData<Bahan> = repository.getBahanById(id)

    fun getAvailableBahan(): LiveData<List<Bahan>> = repository.getAvailableBahan()

    fun insertBahan(bahan: Bahan) = repository.insertBahan(bahan)

    fun updateBahan(bahan: Bahan) = repository.updateBahan(bahan)

    fun deleteBahan(bahan: Bahan) = repository.deleteBahan(bahan)

    //CRUD Bahan On Menu
    fun getAllBahanOnMenu(): LiveData<List<BahanOnMenu>> = repository.getAllBahanOnMenu()

    fun getAllBahanOnMenuById(menuId: Long): LiveData<List<BahanOnMenu>> = repository.getAllBahanOnMenuById(menuId)

    fun insertBahanOnMenu(bahanOnMenu: BahanOnMenu) = repository.insertBahanOnMenu(bahanOnMenu)

    fun insertAllBahanOnMenu(menuId: Long, data: List<BahanOnMenu>) = repository.insertAllBahanOnMenu(menuId, data)

    fun updateBahanOnMenu(bahanOnMenu: BahanOnMenu) = repository.updateBahanOnMenu(bahanOnMenu)

    fun deleteBahanOnMenu(bahanOnMenu: BahanOnMenu) = repository.deleteBahanOnMenu(bahanOnMenu)

    fun updateAllBahanOnMenu(menuId: Long, data: List<BahanOnMenu>) = repository.updateAllBahanOnMenu(menuId, data)

    //CRUD Masak
    fun getAllMasak(): LiveData<List<Masak>> = repository.getAllMasak()

    fun updateOrInsertMasak(newMasak: Masak) = repository.updateOrInsertMasak(newMasak)

    fun insertMasak(masak: Masak) = repository.insertMasak(masak)

    fun updateMasak(masak: Masak) = repository.updateMasak(masak)

    fun deleteMasak(masak: Masak) = repository.deleteMasak(masak)

    fun increaseMasakCount(menuId: Long, date: String) = repository.increaseMasakCount(menuId, date)

    //CRUD Report
    fun getAllReport(): LiveData<List<Report>> = repository.getAllReport()

    fun getExistReport(date: String): LiveData<Boolean?> = repository.getExistReport(date)

    fun getAllReportWithMasak(): LiveData<List<ReportWithMasak>> = repository.getAllReportWithMasak()

    fun insertReport(report: Report) = repository.insertReport(report)

    fun updateReport(report: Report) = repository.updateReport(report)

    fun deleteReport(report: Report) = repository.deleteReport(report)
}