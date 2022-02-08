package com.example.kalkulatormasak.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kalkulatormasak.*
import com.example.kalkulatormasak.databinding.FragmentMenuBinding
import com.example.kalkulatormasak.menu.MenuAdapter.ViewHolder.Companion.REQUEST_ADD
import com.example.kalkulatormasak.model.Masak

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        val adapter = MenuAdapter(requireContext()) { menu, requestCode, qty ->
            if (requestCode == REQUEST_ADD) {
                if (!checkGudang(menu.listBahan, qty)) {
                    Toast.makeText(requireContext(), "Bahan Tidak Cukup!", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in menu.listBahan) {
                        viewModel.decreaseQty(i.bahan.bahanId, i.bahan.qty*qty)
                    }
                    val masak = Masak()
                    masak.menuId = menu.menu.menuId
                    masak.menuName = menu.menu.name
                    masak.reportDate = getDate()
                    masak.count = qty
                    viewModel.updateOrInsertMasak(masak)
                }
            } else {
                val activity = requireActivity() as MainActivity
                val bundle = Bundle()
                bundle.putInt("requestCode", requestCode)
                bundle.putParcelable("data", menu)
                activity.replaceFragment(TambahMenuFragment(), bundle)
            }
        }

        binding.rvMenu.apply {
            this.adapter = adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getMenuWithBahan().observe(requireActivity()) {
            adapter.setData(it)
        }

        binding.btnAddMenu.setOnClickListener {
            val activity = requireActivity() as MainActivity
            val bundle = Bundle()
            bundle.putInt("requestCode", REQUEST_ADD)
            activity.replaceFragment(TambahMenuFragment(), bundle)
        }

        return binding.root
    }
}