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
import com.dapascript.catmov.databinding.FragmentPopularMoviesBinding
import com.dapascript.catmov.presentation.adapter.LoadingPageStateAdapter
import com.dapascript.catmov.presentation.adapter.movie.PopularMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var binding: FragmentPopularMoviesBinding
    private val viewModel: PopularMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized) {
            binding
        } else {
            binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectUiState()
    }

    private fun initView() {
        popularMoviesAdapter = PopularMoviesAdapter()
        binding.rvMovie.apply {
            adapter = popularMoviesAdapter.withLoadStateFooter(
                footer = LoadingPageStateAdapter {
                    popularMoviesAdapter.retry()
                }
            )
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNowPlayingMovies().collectLatest {
                popularMoviesAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            popularMoviesAdapter.loadStateFlow.collectLatest { loadState ->
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
                        if (popularMoviesAdapter.itemCount == 0) {
                            binding.progressBar.visibility = View.GONE
                            binding.llError.visibility = View.VISIBLE
                            binding.btnRetry.setOnClickListener {
                                popularMoviesAdapter.retry()
                            }
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.llError.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Error: ${loadState.refresh}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}