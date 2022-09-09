package com.dapascript.catmov.presentation.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val topViewModel: TopTVViewModel by viewModels()
    private val popularTvViewModel: PopularTVViewModel by viewModels()

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

        topAdapter = TopTVAdapter()
        popularAdapter = PopularTVAdapter()
        initView()
        collectUiState()
    }

    private fun initView() {
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
            topViewModel.getTopRatedTV().collectLatest {
                topAdapter.submitData(it)
            }
        }

        // Popular TV Show
        viewLifecycleOwner.lifecycleScope.launch {
            popularTvViewModel.getPopularTV().collectLatest {
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
                            progressBar.visibility = View.GONE
                            llError.visibility = View.VISIBLE
                            btnRetry.setOnClickListener {
                                popularAdapter.retry()
                            }
                            val result = loadState.refresh as LoadState.Error
                            Toast.makeText(context, result.error.message, Toast.LENGTH_SHORT).show()
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