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
import com.dapascript.catmov.presentation.adapter.PopularMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
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
                binding.progressBar.visibility =
                    if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                binding.rvMovie.visibility =
                    if (loadState.refresh is LoadState.Loading) View.GONE else View.VISIBLE

                val errorState = when {
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    else -> null
                }

                errorState?.let {
                    Toast.makeText(requireActivity(), it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}