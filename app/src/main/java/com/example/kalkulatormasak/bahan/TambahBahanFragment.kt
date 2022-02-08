package com.example.kalkulatormasak.bahan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.kalkulatormasak.MainViewModel
import com.example.kalkulatormasak.ViewModelFactory
import com.example.kalkulatormasak.databinding.FragmentTambahBahanBinding
import com.example.kalkulatormasak.getDate
import com.example.kalkulatormasak.model.Bahan
import com.example.kalkulatormasak.model.BahanOnMenu
import com.example.kalkulatormasak.model.Masak
import com.example.kalkulatormasak.model.MenuWithBahan

class TambahBahanFragment : Fragment() {

    private lateinit var binding: FragmentTambahBahanBinding
    private lateinit var viewModel: MainViewModel
    private var data: Bahan? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTambahBahanBinding.inflate(layoutInflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        val requestCode = arguments?.getInt("requestCode")

        if (requestCode == 101) {
            data = arguments?.getParcelable("data")
            binding.tvTitle.text = "Edit Bahan"
            binding.edtName.setText(data?.name)
            binding.edtSatuan.setText(data?.unit)
            binding.edtQty.setText(data?.qty.toString())
        }

        val adapter = createDropdown(arrayOf("L", "g", "Kg"))
        binding.edtSatuan.setAdapter(adapter)

        binding.btnAdd.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val satuan = binding.edtSatuan.text.toString().trim()
            val qty = binding.edtQty.text.toString().trim()

            if (name.isEmpty()) {
                binding.edtName.error = "Field ini tidak boleh kosong!"
                return@setOnClickListener
            }

            if (satuan.isEmpty()) {
                binding.edtSatuan.error = "Field ini tidak boleh kosong!"
                return@setOnClickListener
            }

            if (qty.isEmpty()) {
                binding.edtQty.error = "Field ini tidak boleh kosong!"
                return@setOnClickListener
            }

            val bahan = Bahan()
            bahan.name = name
            bahan.unit = satuan
            bahan.qty = qty.toFloat()
            if (requestCode == 100) {
                viewModel.insertBahan(bahan)
            } else {
                bahan.bahanId = data?.bahanId!!
                viewModel.updateBahan(bahan)
            }

            requireActivity().onBackPressed()
            onDestroy()
        }
        return binding.root
    }

    private fun createDropdown(data: Array<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            data
        )
    }
}