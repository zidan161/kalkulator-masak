package com.example.kalkulatormasak

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kalkulatormasak.databinding.FragmentReportBinding

class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(layoutInflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        val adapter = ReportAdapter {
            val intent = Intent(requireContext(), TableActivity::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReport.apply {
            this.adapter = adapter
            setHasFixedSize(true)
            this.layoutManager = layoutManager
        }

        val reports = viewModel.getAllReportWithMasak()

        reports.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        return binding.root
    }
}