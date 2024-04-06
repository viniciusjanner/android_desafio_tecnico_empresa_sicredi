package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventListBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.common.getGenericAdapterOf
import com.viniciusjanner.desafio.sicredi.presentation.feature.detail.EventDetailArgs
import com.viniciusjanner.desafio.sicredi.util.Utils
import com.viniciusjanner.desafio.sicredi.util.extensions.hide
import com.viniciusjanner.desafio.sicredi.util.extensions.navigateFromRightToLeft
import com.viniciusjanner.desafio.sicredi.util.extensions.onSingleClick
import com.viniciusjanner.desafio.sicredi.util.extensions.resetPositionScroll
import com.viniciusjanner.desafio.sicredi.util.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventListFragment : Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventListViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val eventsAdapter by lazy {
        getGenericAdapterOf { viewGroup ->
            EventListViewHolder.create(viewGroup, imageLoader) { event, _ ->
                navigateToEventDetail(event)
            }
        }
    }

    private fun navigateToEventDetail(event: Event) {
        val directions = EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(
            EventDetailArgs(
                eventId = event.id,
            )
        )
        findNavController().navigateFromRightToLeft(directions)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        binding.recyclerEventList.run {
            this.setHasFixedSize(true)
            this.adapter = eventsAdapter
            Utils.getCustomItemDecoration()?.let { this.addItemDecoration(it) }
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                EventListViewModel.UiState.Loading -> updateUiToLoading()

                is EventListViewModel.UiState.Success -> updateUiToSuccess(uiState.events)

                EventListViewModel.UiState.Empty -> updateUiToEmpty()

                EventListViewModel.UiState.Error -> updateUiToError()
            }
        }
    }

    private fun updateUiToLoading() {
        with(binding) {
            includeViewLoading.run {
                scroll?.resetPositionScroll()
                horizontalScroll?.resetPositionScroll()
                shimmer.show()
            }
            viewFlipper.displayedChild = FLIPPER_CHILD_LOADING
        }
    }

    private fun updateUiToSuccess(events: List<Event>?) {
        with(binding) {
            includeViewLoading.shimmer.hide()
            eventsAdapter.submitList(events)
            viewFlipper.displayedChild = FLIPPER_CHILD_SUCCESS
        }
    }

    private fun updateUiToEmpty() {
        with(binding) {
            includeViewLoading.shimmer.hide()
            viewFlipper.displayedChild = FLIPPER_CHILD_EMPTY
        }
    }

    private fun updateUiToError() {
        with(binding) {
            includeViewLoading.shimmer.hide()
            viewFlipper.displayedChild = FLIPPER_CHILD_ERROR
        }
    }

    private fun initListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.hide()
            getEvents()
        }

        binding.includeViewEmpty.buttonAction.onSingleClick {
            getEvents()
        }

        binding.includeViewError.buttonAction.onSingleClick {
            getEvents()
        }
    }

    private fun getEvents() {
        eventsAdapter.submitList(emptyList())
        viewModel.actionGetEvents()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_SUCCESS = 1
        private const val FLIPPER_CHILD_EMPTY = 2
        private const val FLIPPER_CHILD_ERROR = 3
    }
}
