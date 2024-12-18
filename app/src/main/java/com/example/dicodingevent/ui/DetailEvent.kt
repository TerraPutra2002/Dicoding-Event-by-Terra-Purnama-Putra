package com.example.dicodingevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.data.local.FavoriteEventEntity
import com.example.dicodingevent.data.local.FavoriteEventRepository
import com.example.dicodingevent.databinding.ActivityDetailEventBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailEvent : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val favoriteEventRepository: FavoriteEventRepository by lazy { FavoriteEventRepository(application) }
    private var isFavorite: Boolean = false
    private var eventId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)
        val eventName = intent.getStringExtra(EXTRA_EVENT_NAME)
        val eventDescription = intent.getStringExtra(EXTRA_EVENT_DESCRIPTION)
        val eventBeginTime = intent.getStringExtra(EXTRA_EVENT_BEGIN_TIME)
        val eventEndTime = intent.getStringExtra(EXTRA_EVENT_END_TIME)
        val eventImage = intent.getStringExtra(EXTRA_EVENT_IMAGE)
        val eventOwner = intent.getStringExtra(EXTRA_EVENT_OWNER)
        val eventQuota = intent.getIntExtra(EXTRA_EVENT_QUOTA, 0)
        val eventRegistrants = intent.getIntExtra(EXTRA_EVENT_REGISTRANTS, 0)
        val eventLink = intent.getStringExtra(EXTRA_EVENT_LINK)
        val eventCategory = intent.getStringExtra(EXTRA_EVENT_CATEGORY)

        binding.tvEventName.text = eventName
        binding.tvDescription.text = HtmlCompat.fromHtml(eventDescription ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        binding.tvTimeBegin.text = "$eventBeginTime - $eventEndTime"
        binding.tvOwner.text = "Penyelenggara: $eventOwner"
        binding.tvCategory.text = eventCategory

        val remainingQuota = eventQuota - eventRegistrants
        binding.tvQuota.text = "Sisa Kuota: $remainingQuota"

        Glide.with(this).load(eventImage).into(binding.ivDetailEvent)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
            startActivity(intent)
        }

        binding.btnFavorite.setOnClickListener {
            toggleFavorite()
        }

        binding.btnShare.setOnClickListener {
            val shareMessage = "Acara: $eventName\nDeskripsi: $eventDescription\nLink: $eventLink"
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareMessage)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan Acara Melalui"))
        }

        checkIfFavorite()
        Log.d("DetailEvent", "isFavorite for eventId $eventId: $isFavorite")
    }

    private fun checkIfFavorite() {
        lifecycleScope.launch {
            isFavorite = favoriteEventRepository.getFavoriteById(eventId) != null
            updateFavButton()
        }
    }

    private fun updateFavButton() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
            binding.btnFavorite.setColorFilter(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            binding.btnFavorite.setColorFilter(ContextCompat.getColor(this, R.color.red))
        }
    }

    private fun toggleFavorite() {
        lifecycleScope.launch {
            if (isFavorite) {
                favoriteEventRepository.deleteById(eventId)
                Toast.makeText(this@DetailEvent, "Removed from Favorite", Toast.LENGTH_SHORT).show()
            } else {
                val newEvent = FavoriteEventEntity().apply {
                    eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)
                    eventName = intent.getStringExtra(EXTRA_EVENT_NAME)
                    eventDescription = intent.getStringExtra(EXTRA_EVENT_DESCRIPTION)
                    owner = intent.getStringExtra(EXTRA_EVENT_OWNER)
                    quota = intent.getIntExtra(EXTRA_EVENT_QUOTA, 0)
                    imageUrl = intent.getStringExtra(EXTRA_EVENT_IMAGE)
                    beginTime = intent.getStringExtra(EXTRA_EVENT_BEGIN_TIME)
                    endTime = intent.getStringExtra(EXTRA_EVENT_END_TIME)
                    registrant = intent.getIntExtra(EXTRA_EVENT_REGISTRANTS, 0)
                    link = intent.getStringExtra(EXTRA_EVENT_LINK)
                    category = intent.getStringExtra(EXTRA_EVENT_CATEGORY)
                }
                favoriteEventRepository.insert(newEvent)
                Toast.makeText(this@DetailEvent, "Added to Favorite", Toast.LENGTH_SHORT).show()
            }
            isFavorite = !isFavorite
            updateFavButton()
        }
    }

    companion object{
        const val EXTRA_EVENT_ID = "ID"
        const val EXTRA_EVENT_NAME = "NAME"
        const val EXTRA_EVENT_OWNER = "OWNER"
        const val EXTRA_EVENT_DESCRIPTION = "DESCRIPTION"
        const val EXTRA_EVENT_QUOTA = "QUOTA"
        const val EXTRA_EVENT_IMAGE = "IMAGE"
        const val EXTRA_EVENT_BEGIN_TIME = "BEGIN"
        const val EXTRA_EVENT_END_TIME = "END"
        const val EXTRA_EVENT_REGISTRANTS = "REGISTRANTS"
        const val EXTRA_EVENT_LINK = "LINK"
        const val EXTRA_EVENT_CATEGORY = "CATEGORY"
    }
}