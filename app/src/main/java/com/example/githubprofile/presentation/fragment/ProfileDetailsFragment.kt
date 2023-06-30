package com.example.githubprofile.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.data.model.ResponseStates
import com.example.githubprofile.databinding.FragmentProfileDetailsBinding
import com.example.githubprofile.presentation.GithubApplication
import com.example.githubprofile.presentation.viewmodel.GithubViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val githubViewModel: GithubViewModel by activityViewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentProfileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as GithubApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(SELECTED_POSITION_KEY, 0) ?: 0
        viewLifecycleOwner.lifecycleScope.launch {
            githubViewModel.profileReposStateFlow.collect {
                when (it) {
                    is ResponseStates.Success -> {
                        val item = it.response[position]
                        setupData(item)
                    }

                    else -> {
                    }
                }
            }
        }
    }

    private fun setupData(item: ProfileRepo) {
        item.forksCount?.let { forksCount ->
            binding.totalForksValue.text = forksCount.toString()

            if (forksCount >= FORKS_COUNT) {
                binding.ivBadge.isVisible = true
            }
            binding.repoNameValue.text = item.name
            binding.repoUrlValue.text = item.url
            binding.repoDescriptionValue.text = item.description
            binding.defaultBranchValue.text = item.defaultBranch
        }
    }

    companion object {
        const val FORKS_COUNT = 5000
        const val SELECTED_POSITION_KEY = "selectedPosition"
        fun newInstance(position: Int) = ProfileDetailsFragment()
            .apply {
                arguments = Bundle().apply {
                    this.putInt(SELECTED_POSITION_KEY, position)
                }
            }
    }
}