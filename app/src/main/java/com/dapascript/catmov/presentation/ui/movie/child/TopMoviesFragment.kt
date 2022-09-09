package com.dapascript.catmov.presentation.ui.movie.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.dapascript.catmov.databinding.FragmentTopMoviesBinding
import com.dapascript.catmov.presentation.adapter.LoadingPageStateAdapter
import com.dapascript.catmov.presentation.adapter.movie.TopMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopMoviesFragment : Fragment() {

    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var binding: FragmentTopMoviesBinding
    private val viewModel: TopMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized) {
            binding
        } else {
            binding = FragmentTopMoviesBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectUiState()
    }

    private fun initView() {
        topMovieAdapter = TopMovieAdapter()
        binding.rvMovie.apply {
            adapter = topMovieAdapter.withLoadStateFooter(
                footer = LoadingPageStateAdapter {
                    topMovieAdapter.retry()
                }
            )
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }

    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTopMovies().collectLatest { pagingData ->
                topMovieAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            topMovieAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.llError.visibility = View.GONE
                    }
                    is LoadState.NotLoading -> {
                        binding.progressBar.visibility = View.GONE
                        binding.llError.visibility = View.GONE
                    }
                    is LoadState.Error -> {
                        if (topMovieAdapter.itemCount == 0) {
                            binding.progressBar.visibility = View.GONE
                            binding.llError.visibility = View.VISIBLE
                            binding.btnRetry.setOnClickListener {
                                topMovieAdapter.retry()
                            }
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.llError.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                (loadState.refresh as LoadState.Error).error.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}