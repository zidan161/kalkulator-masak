package com.example.kalkulatormasak.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kalkulatormasak.MainViewModel
import com.example.kalkulatormasak.ViewModelFactory
import com.example.kalkulatormasak.bahan.BahanDropdownAdapter
import com.example.kalkulatormasak.bahan.BahanHarianAdapter
import com.example.kalkulatormasak.databinding.FragmentTambahMenuBinding
import com.example.kalkulatormasak.getDate
import com.example.kalkulatormasak.menu.MenuAdapter.ViewHolder.Companion.REQUEST_ADD
import com.example.kalkulatormasak.menu.MenuAdapter.ViewHolder.Companion.REQUEST_EDIT
import com.example.kalkulatormasak.model.*

class TambahMenuFragment : Fragment() {

    private lateinit var binding: FragmentTambahMenuBinding
    private lateinit var viewModel: MainViewModel

    private var bahanSelected: Bahan? = null
    private val listBahan = mutableListOf<Bahan>()
    private val list = mutableListOf<BahanOnMenu>()

    private lateinit var bahanAdapter: BahanHarianAdapter
    private var requestCode = REQUEST_ADD
    private var data: MenuWithBahan? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTambahMenuBinding.inflate(layoutInflater, container, false)

        requestCode = arguments?.getInt("requestCode")!!

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        bahanAdapter = BahanHarianAdapter(true, { id, _ ->
            list.remove(list.find { it.bahanId == id })
        }, {})

        binding.rvBahan.apply {
            adapter = bahanAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        if (requestCode == REQUEST_EDIT) {
            data = arguments?.getParcelable("data")

            binding.tvTitle.text = "Edit Menu Masakan"
            binding.edtName.setText(data?.menu?.name)
            for (i in data!!.listBahan) {
                val bahan = Bahan(i.realBahan.bahanId, i.realBahan.name, i.bahan.qty, i.realBahan.unit)
                list.add(BahanOnMenu(i.bahan.bahanId, data?.menu!!.menuId, i.realBahan.bahanId, i.bahan.qty))
                listBahan.add(bahan)
            }
            bahanAdapter.setData(listBahan)
        }

        val availableBahan = viewModel.getAvailableBahan()

        availableBahan.observe(viewLifecycleOwner) {
            val adapter = BahanDropdownAdapter(requireContext(),
                android.R.layout.simple_dropdown_item_1line, it)

            binding.edtBahan.setAdapter(adapter)

            binding.edtBahan.setOnItemClickListener { _, _, i, _ ->
                bahanSelected = adapter.getItem(i)
                binding.tvUnit.text = bahanSelected?.unit
            }
        }

        binding.btnAddBahan.setOnClickListener {
            val bahan = binding.edtBahan.text.toString()
            val qty = binding.edtQty.text.toString().trim()

            if (bahan.isEmpty()) {
                binding.edtBahan.error = "Field ini tidak boleh kosong!"
                return@setOnClickListener
            } else {
                if (bahanSelected!!.qty < qty.toFloat()) {
                    binding.edtQty.error = "Stok bahan tidak cukup!"
                    return@setOnClickListener
                }
            }

            if (qty.isEmpty()) {
                binding.edtQty.error = "Field ini tidak boleh kosong!"
                return@setOnClickListener
            }

            val addBahan = bahanSelected!!
            addBahan.qty = qty.toFloat()
            listBahan.add(addBahan)

            bahanAdapter.setData(listBahan)

            val add = bahanSelected!!
            val new = BahanOnMenu()
            new.bahanId = add.bahanId
            new.qty = qty.toFloat()
            list.add(new)

            binding.edtBahan.setText("")
            binding.edtQty.setText("")
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.edtName.text.toString().trim()

            if (name.isEmpty()) {
                binding.edtName.error = "Field ini tidak boleh kosong!"
                return@setOnClickListener
            }

            if (listBahan.isEmpty()) {
                Toast.makeText(requireContext(), "Belum Menambahkan Bahan - bahan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val menu = Menu()
            menu.name = name
            if (requestCode == REQUEST_ADD) {
                viewModel.insertMenu(menu)
            } else {
                menu.menuId = data?.menu?.menuId!!
                viewModel.updateMenu(menu)
            }

            viewModel.getMenuByName(name).observe(viewLifecycleOwner) {
                if (it != null) {
                    if (requestCode == REQUEST_ADD) {
                        viewModel.insertAllBahanOnMenu(it.menuId, list)
                    } else {
                        viewModel.updateAllBahanOnMenu(it.menuId, list)
                    }
                    requireActivity().onBackPressed()
                    onDestroy()
                }
            }
        }

        return binding.root
    }

    private fun cook(id: Long, qty: Int) {

        viewModel.getMenuWithBahanById(id).observe(viewLifecycleOwner) { menu ->

            for (i in menu.listBahan) {
                viewModel.decreaseQty(i.bahan.bahanId, i.bahan.qty * qty)
            }
            val masak = Masak()
            masak.menuId = menu.menu.menuId
            masak.menuName = menu.menu.name
            masak.reportDate = getDate()
            masak.count = qty
            viewModel.updateOrInsertMasak(masak)
        }
    }
}