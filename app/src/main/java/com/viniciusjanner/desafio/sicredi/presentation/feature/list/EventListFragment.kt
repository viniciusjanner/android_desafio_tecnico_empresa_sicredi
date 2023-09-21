package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventListBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.common.getGenericAdapterOf
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
        getGenericAdapterOf {
            EventListViewHolder.create(it, imageLoader) { eventItem, _ ->
                val directions = EventListFragmentDirections
                    .actionEventListFragmentToEventDetailFragment(
                        eventItem.id
                    )
                findNavController().navigate(directions)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventsAdapter()
        initObservers()
        initListeners()
    }

    private fun initEventsAdapter() {
        binding.recyclerEventList.run {
            setHasFixedSize(true)
            adapter = eventsAdapter
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            // ViewFlipper
            binding.flipperEvents.displayedChild =
                // State
                when (uiState) {
                    EventListViewModel.UiState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }

                    is EventListViewModel.UiState.Success -> {
                        setShimmerVisibility(false)
                        eventsAdapter.submitList(uiState.eventsList)
                        FLIPPER_CHILD_SUCCESS
                    }

                    EventListViewModel.UiState.Empty -> {
                        setShimmerVisibility(false)
                        eventsAdapter.submitList(emptyList())
                        FLIPPER_CHILD_EMPTY
                    }

                    EventListViewModel.UiState.Error -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_ERROR
                    }
                }
        }

        getEvents()
    }

    private fun initListeners() {
        binding.includeViewError.buttonRetry.setOnClickListener {
            getEvents()
        }
    }

    private fun getEvents() {
        viewModel.actionGetEvents()
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
