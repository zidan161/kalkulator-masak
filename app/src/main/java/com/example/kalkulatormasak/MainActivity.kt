package com.example.kalkulatormasak

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kalkulatormasak.bahan.BahanHarianFragment
import com.example.kalkulatormasak.databinding.ActivityMainBinding
import com.example.kalkulatormasak.databinding.InputAdminBinding
import com.example.kalkulatormasak.menu.MenuFragment
import com.example.kalkulatormasak.model.Report

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "shared"
        const val ADMIN = "admin"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnUnfocus: TextView

    private lateinit var preferences: SharedPreferences
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        checkTheDay()

        btnUnfocus = binding.btnReport

        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame, ReportFragment(), ReportFragment::class.java.simpleName)
            .commit()

        binding.btnReport.setOnClickListener {
            setFocused(it as TextView, btnUnfocus)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, ReportFragment(), ReportFragment::class.java.simpleName)
                .commit()
        }

        binding.btnOrder.setOnClickListener {
            setFocused(it as TextView, btnUnfocus)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, BahanHarianFragment(), BahanHarianFragment::class.java.simpleName)
                .commit()
        }

        binding.btnMasak.setOnClickListener {
            setFocused(it as TextView, btnUnfocus)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, MenuFragment(), MenuFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun showInputAdmin() {
        val dialogBinding = InputAdminBinding.inflate(layoutInflater)
        val dialog = createDialog(this, dialogBinding.root, false)
        dialog.show()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.cancel()
        }

        dialogBinding.btnOk.setOnClickListener {
            val name = dialogBinding.edtName.editText?.text.toString()

            if (name.isEmpty()) {
                dialogBinding.edtName.error = "Harus diisi!"
                return@setOnClickListener
            }

            preferences.edit().putString("admin", name).apply()
            binding.tvAdmin.visibility = View.VISIBLE
            binding.tvAdmin.text = "Admin hari ini:\n$name"
            val report = Report()
            report.admin = name
            report.date = getDate()
            viewModel.insertReport(report)
            dialog.dismiss()
        }
    }

    private fun checkTheDay() {
        val dataToday = viewModel.getExistReport(getDate())

        dataToday.observe(this) {
            if (it != null) {
                if (!it) {
                    preferences.edit().putString(ADMIN, null).apply()
                    showInputAdmin()
                } else {
                    val admin = preferences.getString(ADMIN, null)
                    binding.tvAdmin.visibility = View.VISIBLE
                    binding.tvAdmin.text = "Admin hari ini:\n$admin"
                }
            }
        }
    }

    private fun setFocused(btnFocus: TextView, btnUnfocus: TextView) {
        if (btnFocus == btnUnfocus) return
        btnFocus.setTextColor(Color.WHITE)
        btnFocus.setBackgroundResource(R.drawable.button_rounded)
        btnUnfocus.setTextColor(Color.GRAY)
        btnUnfocus.background = null
        this.btnUnfocus = btnFocus
    }

    fun replaceFragment(fragment: Fragment, bundle: Bundle?) {
        if (bundle != null) fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment, fragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }
}