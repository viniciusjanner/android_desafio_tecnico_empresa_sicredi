package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventDetailBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.feature.checkin.EventCheckinArgs
import com.viniciusjanner.desafio.sicredi.util.Utils
import com.viniciusjanner.desafio.sicredi.util.extensions.formatDateHour
import com.viniciusjanner.desafio.sicredi.util.extensions.formatMoneyBrazil
import com.viniciusjanner.desafio.sicredi.util.extensions.navigateFromBottomToTop
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

        initObserverUI()
        initListeners()
    }

    private fun initObserverUI() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.viewFlipper.displayedChild =
                when (uiState) {
                    EventDetailViewModel.UiState.Loading -> {
                        FLIPPER_CHILD_LOADING
                    }

                    is EventDetailViewModel.UiState.Success -> {
                        val event: Event = uiState.event
                        populateDetailsEvent(event)
                        FLIPPER_CHILD_SUCCESS
                    }

                    EventDetailViewModel.UiState.Error -> {
                        FLIPPER_CHILD_ERROR
                    }
                }
        }

        getEvent()
    }

    private fun initListeners() {
        binding.eventMap.setOnClickListener {
            openAddressInMap()
        }

        binding.buttonShare.setOnClickListener {
            openSharing()
        }

        binding.buttonCheckin.setOnClickListener {
            navigateToEventCheckin()
        }

        binding.includeViewError.buttonAction.setOnClickListener {
            getEvent()
        }
    }

    private fun populateDetailsEvent(event: Event) {
        with(binding) {
            event.image?.let {
                imageLoader.load(eventImage, it)
            }

            eventTitle.text = event.title
            eventDateHour.text = event.date?.formatDateHour()
            eventAddress.text = Utils.convertCoordinatesToAddress(event.latitude!!, event.longitude!!, requireContext())
            eventPrice.text = event.price?.formatMoneyBrazil()
            eventSubtitle.text = event.description
        }
    }

    private fun getEvent() {
        val eventId: String? = args?.eventDetailArgs?.eventId
        eventId?.let {
            viewModel.actionGetEvent(it)
        }
    }

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

    private fun openAddressInMap() {
        try {
            val address: String = binding.eventAddress.text.toString()
            val uriString = "geo:0,0?q=${address}"
            val uri = Uri.parse(uriString)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Nenhum aplicativo disponível!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSharing() {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, messageSharing())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Nenhum aplicativo disponível!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun messageSharing(): String {
        return with(binding) {
            StringBuilder()
                .append("Evento")
                .append("\n\nTitulo: ${eventTitle.text}")
                .append("\n\nData: ${eventDateHour.text}")
                .append("\n\nEndereço: ${eventAddress.text}")
                .append("\n\nPreço: ${eventPrice.text}")
                .append("\n")
                .toString()
        }
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
