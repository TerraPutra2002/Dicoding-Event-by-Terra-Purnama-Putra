package com.example.dicodingevent.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.ListEventAdapter
import com.example.dicodingevent.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var upcomingEventAdapter: ListEventAdapter
    private lateinit var finishedEventAdapter: ListEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        upcomingEventAdapter = ListEventAdapter(requireContext())
        binding.rvEventUpcoming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingEventAdapter
        }

        finishedEventAdapter = ListEventAdapter(requireContext())
        binding.rvEventFinished.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = finishedEventAdapter
        }

        homeViewModel.upcomingEventList.observe(viewLifecycleOwner, Observer { eventList ->
            val limitedEventList = eventList.take(5)
            upcomingEventAdapter.submitList(limitedEventList)
        })

        homeViewModel.finishedEventList.observe(viewLifecycleOwner, Observer { eventList ->
            val limitedEventList = eventList.take(5)
            finishedEventAdapter.submitList(limitedEventList)
        })

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.proBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}