package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventCheckinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventCheckinFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEventCheckinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventCheckinViewModel by viewModels()

    // private val args by navArgs<EventCheckinFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventCheckinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserverCheckin()
        initListeners()
    }

    private fun initObserverCheckin() {
        viewModel.stateCheckin.observe(viewLifecycleOwner) { checkinState ->
            // ViewFlipper
            binding.flipperCheckin.displayedChild =
                when (checkinState) {
                    EventCheckinViewModel.CheckinState.Loading -> {
                        FLIPPER_CHILD_LOADING
                    }

                    is EventCheckinViewModel.CheckinState.Success -> {
                        findNavController().run {
                            previousBackStackEntry?.savedStateHandle?.set(
                                KEY_APPLIED_BASK_STACK,
                                true,
                            )
                            popBackStack()
                        }
                        FLIPPER_CHILD_SUCCESS
                    }

                    EventCheckinViewModel.CheckinState.Error -> {
                        FLIPPER_CHILD_ERROR
                    }
                }
        }
    }

    private fun initListeners() {
        binding.buttonSend.setOnClickListener {
            sendCheckin()
        }

        binding.includeViewError.buttonRetry.setOnClickListener {
            sendCheckin()
        }
    }

    private fun sendCheckin() {
        viewModel.actionSendCheckin(
            EventCheckInSend(
                "1",
                "Janner",
                "viniciusjanner@gmail.com"
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val FLIPPER_CHILD_SUCCESS = 0
        private const val FLIPPER_CHILD_LOADING = 1
        private const val FLIPPER_CHILD_ERROR = 2

        const val KEY_APPLIED_BASK_STACK = "keyAppliedBackStack"
    }
}
