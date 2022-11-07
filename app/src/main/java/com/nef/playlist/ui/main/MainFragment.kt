package com.nef.playlist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.frontparissportifs.utils.DataState
import com.frontparissportifs.utils.displayError
import com.nef.playlist.R
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.data.network.hasNetwork
import com.nef.playlist.databinding.FragmentMainBinding
import com.nef.playlist.ui.utils.ImageLoader
import com.nef.playlist.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(hasNetwork(requireContext())) {
            viewModel.syncData()
        } else {
            displayError(
                requireContext(),
                "Pas de connexion internet, seul le cache est disponible.")
        }

        val adapter = PlaylistAdapter(imageLoader)
        binding.tasksRecyclerView.adapter = adapter

        binding.swipeContainer.setOnRefreshListener {
            viewModel.syncData()
        }

        viewModel.playlistLiveData.observe(viewLifecycleOwner) {
            processResponse(it)
        }

    }

    private fun processResponse(state: DataState<List<PlaylistEntity>>) {

        when(state) {
            is DataState.Error -> {
                displayLoading(false)
                displayError(requireContext(),state.exception.message)
            }
            DataState.Loading -> displayLoading(true)
            is DataState.Success -> {
                displayLoading(false)
                (binding.tasksRecyclerView.adapter as PlaylistAdapter).submitList(state.data)
            }
        }

    }

    private fun displayLoading(isLoading: Boolean) {
        binding.swipeContainer.isRefreshing = isLoading
    }

}