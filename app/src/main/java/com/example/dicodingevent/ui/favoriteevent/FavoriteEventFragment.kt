package com.example.dicodingevent.ui.favoriteevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.ListEventAdapter
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.FragmentFavoriteEventBinding

class FavoriteEventFragment : Fragment() {

    private var _binding: FragmentFavoriteEventBinding? = null
    private val binding get() = checkNotNull(_binding)
    private lateinit var favoriteEventViewModel: FavoriteEventViewModel
    private lateinit var eventAdapter: ListEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = ListEventAdapter(requireContext())
        binding.rvFavoriteEvent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }

        favoriteEventViewModel = ViewModelProvider(this).get(FavoriteEventViewModel::class.java)

        favoriteEventViewModel.favoriteList.observe(viewLifecycleOwner, Observer { favoriteEvents ->
            val favoriteListItems: List<ListEventsItem> = favoriteEvents.map { event ->
                ListEventsItem(
                    id = event.eventId,
                    name = event.eventName ?: "",
                    description = event.eventDescription ?: "",
                    link = event.link ?: "",
                    ownerName = event.owner ?: "",
                    quota = event.quota ?: 0,
                    imageLogo = event.imageUrl ?: "",
                    beginTime = event.beginTime ?: "",
                    endTime = event.endTime ?: "",
                    registrants = event.registrant ?: 0,
                    category = event.category ?: "",
                    cityName = event.cityName ?: "",
                    mediaCover = event.mediaCover ?: "",
                    summary = event.summary ?: ""
                )
            }
            eventAdapter.submitList(favoriteListItems)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}