package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventCheckinBinding
import com.viniciusjanner.desafio.sicredi.presentation.feature.checkin.EventCheckinViewModel.*
import com.viniciusjanner.desafio.sicredi.util.extensions.onSingleClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventCheckinFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEventCheckinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventCheckinViewModel by viewModels()

    private val args by navArgs<EventCheckinFragmentArgs>()

    override fun getTheme(): Int = R.style.Theme_Widget_BottomSheet

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventCheckinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initBottomSheetDialog()
        initObserverCheckin()
        initListeners()
    }

    private fun initViews() {
        binding.buttonAction.isEnabled = viewModel.isEnableButton()
    }

    private fun initBottomSheetDialog() {
        dialog?.let {
            val sheet = it as BottomSheetDialog
            // Anim
            sheet.window?.attributes?.windowAnimations = R.style.Theme_Widget_Anim_Dialog
            // State
            sheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            // Collapsed
            sheet.behavior.skipCollapsed = true
            // Exibir todos os itens do layout (se possivel).
            sheet.behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
        }
    }

    private fun initObserverCheckin() {
        viewModel.state.observe(viewLifecycleOwner) { checkinState ->
            binding.viewFlipper.displayedChild =
                when (checkinState) {
                    UiState.Loading -> {
                        FLIPPER_CHILD_LOADING
                    }

                    is UiState.Success -> {
                        FLIPPER_CHILD_SUCCESS
                    }

                    UiState.Error -> {
                        FLIPPER_CHILD_ERROR
                    }
                }
        }

        viewModel.enableButtonMediator.observe(viewLifecycleOwner) { isEnabled ->
            binding.buttonAction.isEnabled = (isEnabled == true)
        }

        viewModel.errorName.observe(viewLifecycleOwner) {
            binding.tilName.error =
                when (it) {
                    ErrorMessage.INVALID -> getString(R.string.common_error_invalid_name)
                    ErrorMessage.REQUIRED -> getString(R.string.common_error_required)
                    ErrorMessage.NONE -> null
                }
        }

        viewModel.errorEmail.observe(viewLifecycleOwner) {
            binding.tilEmail.error =
                when (it) {
                    ErrorMessage.INVALID -> getString(R.string.common_error_invalid_email)
                    ErrorMessage.REQUIRED -> getString(R.string.common_error_required)
                    ErrorMessage.NONE -> null
                }
        }
    }

    private fun initListeners() {
        binding.tietName.doAfterTextChanged { text ->
            viewModel.setName(text.toString())
        }

        binding.tietEmail.doAfterTextChanged { text ->
            viewModel.setEmail(text.toString())
        }

        binding.buttonAction.onSingleClick {
            sendCheckin()
        }

        binding.includeViewError.buttonAction.onSingleClick {
            sendCheckin()
        }

        binding.includeViewSuccess.buttonAction.onSingleClick {
            navigateToEventDetail()
        }
    }

    private fun sendCheckin() {
        val eventId: String? = args?.eventCheckinArgs?.eventId
        eventId?.let {
            viewModel.actionSendCheckin(
                EventCheckinSend(
                    eventId = it,
                    name = binding.tietName.text.toString().trim(),
                    email = binding.tietEmail.text.toString().trim(),
                )
            )
        }
    }

    private fun navigateToEventDetail() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @Suppress("unused")
        private const val FLIPPER_CHILD_INITIAL = 0
        private const val FLIPPER_CHILD_LOADING = 1
        private const val FLIPPER_CHILD_SUCCESS = 2
        private const val FLIPPER_CHILD_ERROR = 3
    }
}
