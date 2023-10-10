package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.databinding.FragmentEventListBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.common.MarginItemDecoration
import com.viniciusjanner.desafio.sicredi.presentation.common.getGenericAdapterOf
import com.viniciusjanner.desafio.sicredi.presentation.feature.detail.EventDetailArgs
import com.viniciusjanner.desafio.sicredi.util.extensions.hide
import com.viniciusjanner.desafio.sicredi.util.extensions.navigateFromRightToLeft
import com.viniciusjanner.desafio.sicredi.util.extensions.onSingleClick
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
            addItemDecoration(customItemDecoration())
        }
    }

    private fun customItemDecoration(): RecyclerView.ItemDecoration =
        MarginItemDecoration(
            resources.getDimensionPixelSize(R.dimen.item_cardview_with_elevation_margin_top),
            resources.getDimensionPixelSize(R.dimen.item_cardview_with_elevation_margin_left),
            resources.getDimensionPixelSize(R.dimen.item_cardview_with_elevation_margin_right),
            resources.getDimensionPixelSize(R.dimen.item_cardview_with_elevation_margin_bottom),
            resources.getDimensionPixelSize(R.dimen.item_cardview_with_elevation_margin_bottom_last),
        )

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            with(binding) {
                viewFlipper.displayedChild =
                    when (uiState) {
                        EventListViewModel.UiState.Loading -> {
                            includeViewLoading.shimmer.show()
                            includeViewLoading.scrollView.scrollTo(0, 0)
                            FLIPPER_CHILD_LOADING
                        }

                        is EventListViewModel.UiState.Success -> {
                            includeViewLoading.shimmer.hide()
                            eventsAdapter.submitList(uiState.events)
                            FLIPPER_CHILD_SUCCESS
                        }

                        EventListViewModel.UiState.Empty -> {
                            includeViewLoading.shimmer.hide()
                            eventsAdapter.submitList(emptyList())
                            FLIPPER_CHILD_EMPTY
                        }

                        EventListViewModel.UiState.Error -> {
                            includeViewLoading.shimmer.hide()
                            FLIPPER_CHILD_ERROR
                        }
                    }
            }
        }

        getEvents()
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
