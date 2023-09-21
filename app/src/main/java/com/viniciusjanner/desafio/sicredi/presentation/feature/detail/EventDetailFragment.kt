package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventDetailBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.util.extensions.formatDateHour
import com.viniciusjanner.desafio.sicredi.util.extensions.formatMoney
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventDetailViewModel by viewModels()

    private val args by navArgs<EventDetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            // ViewFlipper
            binding.flipperEvents.displayedChild =
                    // State
                when (uiState) {
                    EventDetailViewModel.UiState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }

                    is EventDetailViewModel.UiState.Success -> {
                        setShimmerVisibility(false)

                        val event = uiState.event
                        binding.apply {
                            val eventImageUrl = args.eventDetailViewArg.eventImageUrl
                            imageLoader.load(eventImage, eventImageUrl)

                            eventTitle.text = event.title
                            eventDateHour.text = event.date?.formatDateHour()
                            eventPrice.text = event.price?.formatMoney()
                            eventSubtitle.text = event.description
                        }

                        FLIPPER_CHILD_SUCCESS
                    }

                    EventDetailViewModel.UiState.Error -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_ERROR
                    }
                }
        }

        getEventItem()
    }

    private fun initListeners() {
        binding.buttonShare.setOnClickListener {
            shareEvent()
        }

        binding.buttonCheckin.setOnClickListener {

        }

        binding.includeViewError.buttonRetry.setOnClickListener {

        }
    }

    private fun getEventItem() {
        val eventId = args.eventDetailViewArg.eventId
        viewModel.actionLoad(eventId)
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewLoading.shimmerEvents.run {
            this.isVisible = visibility
            if (visibility) {
                startShimmer()
            } else {
                stopShimmer()
            }
        }
    }

    private fun shareEvent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, args.eventDetailViewArg.toShareEvent())
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_SUCCESS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}
