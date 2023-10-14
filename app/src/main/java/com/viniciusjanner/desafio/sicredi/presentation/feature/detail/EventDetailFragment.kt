package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventDetailBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.feature.checkin.EventCheckinArgs
import com.viniciusjanner.desafio.sicredi.util.Utils
import com.viniciusjanner.desafio.sicredi.util.extensions.formatDateHour
import com.viniciusjanner.desafio.sicredi.util.extensions.formatMoneyBrazil
import com.viniciusjanner.desafio.sicredi.util.extensions.hide
import com.viniciusjanner.desafio.sicredi.util.extensions.navigateFromBottomToTop
import com.viniciusjanner.desafio.sicredi.util.extensions.onSingleClick
import com.viniciusjanner.desafio.sicredi.util.extensions.resetPositionScroll
import com.viniciusjanner.desafio.sicredi.util.extensions.show
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavArgs()
        initObservers()
        initListeners()
    }

    @Suppress("unnecessary_safe_call")
    private fun setNavArgs() {
        args?.eventDetailArgs?.let {
            viewModel.setSavedStateHandle(it)
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                EventDetailViewModel.UiState.Loading -> updateUiToLoading()

                is EventDetailViewModel.UiState.Success -> updateUiToSuccess(uiState.event)

                EventDetailViewModel.UiState.Error -> updateUiToError()
            }
        }
    }

    private fun updateUiToLoading() {
        with(binding) {
            includeViewLoading.run {
                nestedScroll.resetPositionScroll()
                shimmer.show()
            }
            viewFlipper.displayedChild = FLIPPER_CHILD_LOADING
        }
    }

    private fun updateUiToSuccess(event: Event) {
        with(binding) {
            populateDetailsEvent(event)
            includeViewLoading.shimmer.hide()
            viewFlipper.displayedChild = FLIPPER_CHILD_SUCCESS
        }
    }

    private fun updateUiToError() {
        with(binding) {
            includeViewLoading.shimmer.hide()
            viewFlipper.displayedChild = FLIPPER_CHILD_ERROR
        }
    }

    private fun populateDetailsEvent(event: Event) {
        with(binding) {
            event.image?.let {
                imageLoader.load(eventImage, it)
            }

            eventTitle.text = event.title

            eventDateHour.text = event.date?.formatDateHour()

            val peopleNumbers: Int = (event.people?.size ?: 0)
            eventPeople.text = getString(R.string.screen_event_detail_people_param, peopleNumbers.toString())

            eventPrice.text = event.price?.formatMoneyBrazil()

            Utils.convertCoordinatesToAddressString(event.latitude!!, event.longitude!!) { addressString ->
                eventAddress.text = addressString
            }

            eventDescription.text = event.description
        }
    }

    private fun initListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.hide()
            getEvent()
        }

        binding.eventMap.onSingleClick {
            openAddressInMap()
        }

        binding.buttonShare.onSingleClick {
            openMessageSharing()
        }

        binding.buttonCheckin.onSingleClick {
            navigateToEventCheckin()
        }

        binding.includeViewError.buttonAction.onSingleClick {
            getEvent()
        }
    }

    @Suppress("unnecessary_safe_call")
    private fun getEvent() {
        clearDetailsEvent()

        val eventId: String? = args?.eventDetailArgs?.eventId
        eventId?.let {
            viewModel.actionGetEvent(it)
        }
    }

    private fun clearDetailsEvent() {
        with(binding) {
            eventImage.setImageDrawable(null)

            eventTitle.text = null

            eventDateHour.text = null

            eventPeople.text = null

            eventPrice.text = null

            eventAddress.text = null

            eventDescription.text = null
        }
    }

    private fun openAddressInMap() {
        val address: String = binding.eventAddress.text.toString()
        Utils.openAppMap(address)
    }

    private fun openMessageSharing() {
        val messageSharing =
            with(binding) {
                getString(
                    R.string.screen_event_detail_message_sharing,
                    eventTitle.text,
                    eventDateHour.text,
                    eventAddress.text,
                    eventPrice.text,
                )
            }
        Utils.openAppSharing(messageSharing)
    }

    @Suppress("unnecessary_safe_call")
    private fun navigateToEventCheckin() {
        val eventId: String? = args?.eventDetailArgs?.eventId
        eventId?.let {
            val directions = EventDetailFragmentDirections.actionEventDetailFragmentToEventCheckinFragment(
                EventCheckinArgs(
                    eventId = eventId,
                )
            )
            findNavController().navigateFromBottomToTop(directions)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 1
        private const val FLIPPER_CHILD_SUCCESS = 0
        private const val FLIPPER_CHILD_ERROR = 2
    }
}
