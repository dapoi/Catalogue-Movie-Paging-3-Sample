package com.dapascript.catmov.presentation.ui.tv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapascript.catmov.databinding.FragmentTvShowBinding
import com.dapascript.catmov.presentation.adapter.LoadingPageStateAdapter
import com.dapascript.catmov.presentation.adapter.tv.PopularTVAdapter
import com.dapascript.catmov.presentation.adapter.tv.TopTVAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TVFragment : Fragment() {

    private lateinit var topAdapter: TopTVAdapter
    private lateinit var popularAdapter: PopularTVAdapter

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val tvViewModel: TVViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectUiState()
    }

    private fun initView() {
        topAdapter = TopTVAdapter()
        popularAdapter = PopularTVAdapter()

        // Top TV
        binding.rvTopRatedTvShow.apply {
            adapter = topAdapter.withLoadStateFooter(
                footer = LoadingPageStateAdapter {
                    topAdapter.retry()
                }
            )
            layoutManager = LinearLayoutManager(requireActivity())
        }

        // Popular TV
        binding.rvPopularTvShow.apply {
            adapter = popularAdapter.withLoadStateFooter(
                footer = LoadingPageStateAdapter {
                    popularAdapter.retry()
                }
            )
        }
    }

    private fun collectUiState() {

        // Top Rated TV Show
        viewLifecycleOwner.lifecycleScope.launch {
            tvViewModel.getTopRatedTV().collectLatest {
                topAdapter.submitData(it)
            }
        }

        // Popular TV Show
        viewLifecycleOwner.lifecycleScope.launch {
            tvViewModel.getPopularTV().collectLatest {
                popularAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            popularAdapter.loadStateFlow.collectLatest { loadState ->
                binding.apply {
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            llError.visibility = View.GONE
                        }
                        is LoadState.NotLoading -> {
                            progressBar.visibility = View.GONE
                            llError.visibility = View.GONE
                        }
                        is LoadState.Error -> {
                            if (popularAdapter.itemCount == 0) {
                                progressBar.visibility = View.GONE
                                llError.visibility = View.VISIBLE
                                btnRetry.setOnClickListener {
                                    topAdapter.retry()
                                    popularAdapter.retry()
                                }
                            } else {
                                progressBar.visibility = View.GONE
                                llError.visibility = View.GONE
                                Log.e(
                                    "Error",
                                    (loadState.refresh as LoadState.Error).error.message.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}