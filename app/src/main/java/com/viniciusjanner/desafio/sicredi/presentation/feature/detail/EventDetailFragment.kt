package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventDetailBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.feature.checkin.EventCheckinFragment
import com.viniciusjanner.desafio.sicredi.presentation.feature.checkin.EventCheckinViewArg
import com.viniciusjanner.desafio.sicredi.util.extensions.formatDateHour
import com.viniciusjanner.desafio.sicredi.util.extensions.formatMoney
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale
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
        observeCheckinData()
    }

    private fun initObserverUI() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            // ViewFlipper
            binding.flipperEventDetail.displayedChild =
                when (uiState) {
                    EventDetailViewModel.UiState.Loading -> {
                        FLIPPER_CHILD_LOADING
                    }

                    is EventDetailViewModel.UiState.Success -> {
                        val event = uiState.event
                        val eventImageUrl = args.eventDetailViewArg.eventImageUrl
                        imageLoader.load(binding.eventImage, eventImageUrl)

                        binding.eventTitle.text = event.title
                        binding.eventDateHour.text = event.date?.formatDateHour()
                        binding.eventPrice.text = event.price?.formatMoney()
                        binding.eventSubtitle.text = event.description

                        FLIPPER_CHILD_SUCCESS
                    }

                    EventDetailViewModel.UiState.Error -> {
                        FLIPPER_CHILD_ERROR
                    }
                }
        }

        getEventItem()
    }

    private fun initListeners() {
        binding.eventLocal.setOnClickListener {
            openLocalization()
        }

        binding.buttonShare.setOnClickListener {
            openSharing()
        }

        binding.buttonCheckin.setOnClickListener {
            sendCheckin()
        }

        binding.includeViewError.buttonRetry.setOnClickListener {
            getEventItem()
        }
    }

    private fun getEventItem() {
        val eventId = args.eventDetailViewArg.eventId
        viewModel.actionLoadEvent(eventId)
    }

    private fun sendCheckin() {
        val directions = EventDetailFragmentDirections
            .actionEventDetailFragmentToEventCheckinFragment(
                EventCheckinViewArg(
                    eventId = args.eventDetailViewArg.eventId,
                )
            )
        findNavController().navigate(directions)
    }

    private fun openSharing() {
        val eventText = args.eventDetailViewArg.toShareEvent()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, eventText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun openLocalization() {
        val latitude = args.eventDetailViewArg.eventLatitude
        val longitude = args.eventDetailViewArg.eventLongitude
        val address = convertCoordinatorsToAddress(requireContext(), latitude, longitude)

        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        startActivity(mapIntent)
    }

    private fun convertCoordinatorsToAddress(context: Context, latitude: Double?, longitude: Double?): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            if (latitude != 0.0 && longitude != 0.0) {
                val addressList = geocoder.getFromLocation(latitude!!, longitude!!, 1)

                if (addressList != null && addressList.isNotEmpty()) {
                    val address = addressList[0]
                    val addressFull = StringBuilder()

                    // Adicione o nome da rua, se disponível
                    address.thoroughfare?.let { addressFull.append(it) }

                    // Adicione a cidade, se disponível
                    address.locality?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    // Adicione o estado, se disponível
                    address.adminArea?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    // Adicione o país, se disponível
                    address.countryName?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    // Adicione o CEP, se disponível
                    address.postalCode?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    return addressFull.toString()
                }
            } else {
                // Endereço não encontrado
                return ""
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // Endereço não encontrado
        return ""
    }

    private fun observeCheckinData() {
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.EventDetailFragment)

        val observer = LifecycleEventObserver { _, event ->
            val isCheckinApplied =
                navBackStackEntry.savedStateHandle.contains(
                    EventCheckinFragment.KEY_APPLIED_BASK_STACK
                )

            if (event == Lifecycle.Event.ON_RESUME && isCheckinApplied) {
                navBackStackEntry.savedStateHandle.remove<Boolean>(
                    EventCheckinFragment.KEY_APPLIED_BASK_STACK
                )
            }
        }

        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    navBackStackEntry.lifecycle.removeObserver(observer)
                }
            },
        )
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
