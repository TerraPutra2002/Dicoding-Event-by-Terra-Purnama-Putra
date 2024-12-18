package com.example.dicodingevent.ui.upcomingevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.ListEventAdapter
import com.example.dicodingevent.databinding.FragmentUpcomingEventBinding

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = checkNotNull(_binding) {  }
    private lateinit var upcomingEventViewModel: UpcomingEventViewModel
    private lateinit var listEventAdapter: ListEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listEventAdapter = ListEventAdapter(requireContext())
        binding.rvUpcomingEvent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listEventAdapter
        }

        upcomingEventViewModel = ViewModelProvider(this).get(UpcomingEventViewModel::class.java)

        upcomingEventViewModel.upcomingEventList.observe(viewLifecycleOwner, Observer { eventList ->
            listEventAdapter.submitList(eventList)
        })

        upcomingEventViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.barProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
