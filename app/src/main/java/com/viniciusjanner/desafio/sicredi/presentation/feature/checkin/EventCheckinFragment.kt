package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventCheckinBinding
import com.viniciusjanner.desafio.sicredi.util.validation.PatternValidation
import com.viniciusjanner.desafio.sicredi.util.validation.ValidaEmail
import com.viniciusjanner.desafio.sicredi.util.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventCheckinFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEventCheckinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventCheckinViewModel by viewModels()

    private val args by navArgs<EventCheckinFragmentArgs>()

    private val validators = mutableListOf<Validator>()

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
        validateFieldName()
        validateFieldEmail()
    }

    private fun initObserverCheckin() {
        viewModel.stateCheckin.observe(viewLifecycleOwner) { checkinState ->
            // ViewFlipper
            binding.flipperCheckin.displayedChild =
                when (checkinState) {
                    EventCheckinViewModel.UiState.Loading -> {
                        FLIPPER_CHILD_LOADING
                    }

                    is EventCheckinViewModel.UiState.Success -> {
                        FLIPPER_CHILD_SUCCESS
                    }

                    EventCheckinViewModel.UiState.Error -> {
                        FLIPPER_CHILD_ERROR
                    }
                }
        }
    }

    private fun initListeners() {
        binding.buttonSend.setOnClickListener {
            validateCheckin()
        }

        binding.includeViewError.buttonRetry.setOnClickListener {
            sendCheckin()
        }

        binding.includeViewSuccess.buttonClose.setOnClickListener {
            navigateToEventDetail()
        }
    }

    private fun validateFieldEmail() {
        val fieldEmail: EditText? = binding.tilEmail.editText
        val validator = ValidaEmail(binding.tilEmail)
        validators.add(validator)
        fieldEmail!!.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validator.isValid
            }
        }
    }

    private fun validateFieldName() {
        val field = binding.tilName.editText
        val validator = PatternValidation(binding.tilName)
        validators.add(validator)
        field!!.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validator.isValid
            }
        }
    }

    private fun validAllFields(): Boolean {
        var formIsValid = true
        for (validator in validators) {
            if (!validator.isValid) {
                formIsValid = false
            }
        }
        return formIsValid
    }

    private fun validateCheckin() {
        val formIsValid: Boolean = validAllFields()
        if (formIsValid) {
            sendCheckin()
        }
    }

    private fun sendCheckin() {
        viewModel.actionSendCheckin(
            EventCheckInSend(
                args.eventCheckinViewArg.eventId,
                binding.tietName.text.toString().trim(),
                binding.tietEmail.text.toString().trim(),
            )
        )
    }

    private fun navigateToEventDetail() {
        findNavController().run {
            previousBackStackEntry?.savedStateHandle?.set(
                KEY_APPLIED_BASK_STACK,
                true,
            )
            popBackStack()
        }
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

        const val KEY_APPLIED_BASK_STACK = "keyAppliedBackStack"
    }
}
