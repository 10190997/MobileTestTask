package com.example.mobiletesttask.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletesttask.R
import com.example.mobiletesttask.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private val viewModel: OverviewViewModel by viewModels()
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MovieListAdapter {
            val action =
                OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnRetry.setOnClickListener {
            viewModel.getMoviesList()
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.status.observe(viewLifecycleOwner) {
            bindStatus(it)
        }

        viewModel.getMoviesList()
    }

    private fun bindStatus(it: ApiStatus?) {
        when (it) {
            ApiStatus.LOADING -> {
                binding.statusImage.visibility = View.VISIBLE
                binding.statusImage.setImageResource(R.drawable.loading_animation)
                binding.btnRetry.visibility = View.GONE
            }

            ApiStatus.ERROR -> {
                binding.statusImage.visibility = View.VISIBLE
                binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                binding.btnRetry.visibility = View.VISIBLE
            }

            else -> {
                binding.statusImage.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            }
        }
    }
}