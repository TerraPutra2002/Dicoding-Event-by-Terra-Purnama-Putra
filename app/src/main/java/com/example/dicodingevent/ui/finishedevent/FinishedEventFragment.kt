package com.example.dicodingevent.ui.finishedevent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.ListEventAdapter
import com.example.dicodingevent.databinding.FragmentFinishedEventBinding

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var finishedEventViewModel: FinishedEventViewModel
    private lateinit var listEventAdapter: ListEventAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listEventAdapter = ListEventAdapter(requireContext())
        binding.rvFinishedEvent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listEventAdapter
        }

        finishedEventViewModel = ViewModelProvider(this).get(FinishedEventViewModel::class.java)

        finishedEventViewModel.finishedEventList.observe(viewLifecycleOwner, Observer { eventList ->
            Log.d("FinishedEventFragment", "Data received in fragment: ${eventList?.size}")
            listEventAdapter.submitList(eventList)
        })

        finishedEventViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

