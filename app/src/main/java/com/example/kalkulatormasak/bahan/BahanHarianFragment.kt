package com.example.kalkulatormasak.bahan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kalkulatormasak.MainActivity
import com.example.kalkulatormasak.MainViewModel
import com.example.kalkulatormasak.ViewModelFactory
import com.example.kalkulatormasak.databinding.FragmentBahanHarianBinding
import com.example.kalkulatormasak.hideKeyboard

class BahanHarianFragment : Fragment() {

    private lateinit var binding: FragmentBahanHarianBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: BahanHarianAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBahanHarianBinding.inflate(layoutInflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        val bahan = viewModel.getAllBahan()

        adapter = BahanHarianAdapter(false, { id, qty ->
            viewModel.increaseQty(id, qty)
            hideKeyboard()
        }, {
            val activity = requireActivity() as MainActivity
            val bundle = Bundle()
            bundle.putParcelable("data", it)
            bundle.putInt("requestCode", 101)
            activity.replaceFragment(TambahBahanFragment(), bundle)
        })

        binding.rvBahan.adapter = adapter
        binding.rvBahan.setHasFixedSize(true)
        binding.rvBahan.layoutManager = LinearLayoutManager(requireContext())

        bahan.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.btnAddBahan.setOnClickListener {
            val activity = requireActivity() as MainActivity
            val bundle = Bundle()
            bundle.putInt("requestCode", 100)
            activity.replaceFragment(TambahBahanFragment(), bundle)
        }

        return binding.root
    }
}