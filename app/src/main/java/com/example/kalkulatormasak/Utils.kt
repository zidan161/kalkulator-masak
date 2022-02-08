package com.example.kalkulatormasak

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.kalkulatormasak.model.BahanWithRealBahan
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDate(): String {
    return SimpleDateFormat("EEE, dd-MMM-yyyy", Locale("id")).format(Date())
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun createDialog(ctx: Context, v: View, cancelable: Boolean): AlertDialog {
    return AlertDialog.Builder(ctx)
        .setView(v)
        .setCancelable(cancelable)
        .create()
}

fun checkGudang(bahan: List<BahanWithRealBahan>, qty: Int): Boolean {
    var isAda = true
    for (i in bahan) {
        if (i.realBahan.qty < i.bahan.qty*qty) isAda = false
    }
    return isAda
}

fun checkGudang(bahan: List<BahanWithRealBahan>): Int? {
    val allCount = arrayListOf<Int>()
    bahan.forEach { item ->
        var qty = 0
        while (true) {
            if (item.realBahan.qty <= item.bahan.qty * qty++) {
                qty--
                break
            }
        }
        allCount.add(qty)
    }
    return allCount.minOrNull()
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}