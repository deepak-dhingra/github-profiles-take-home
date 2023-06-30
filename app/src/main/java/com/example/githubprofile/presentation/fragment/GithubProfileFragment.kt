package com.example.githubprofile.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprofile.R
import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.data.model.ResponseStates
import com.example.githubprofile.databinding.FragmentGithubProfileBinding
import com.example.githubprofile.presentation.GithubApplication
import com.example.githubprofile.presentation.adapter.ReposAdapter
import com.example.githubprofile.presentation.viewmodel.GithubViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val githubViewModel: GithubViewModel by activityViewModels {
        viewModelFactory
    }

    private lateinit var viewBinding: FragmentGithubProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as GithubApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentGithubProfileBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewBinding.searchButton.setOnClickListener {
            val userId = viewBinding.searchEditText.text.toString()
            if (userId.isNotEmpty()) {
                githubViewModel.getGithubProfile(userId)
            } else {
                viewBinding.searchEditText.error = getString(R.string.edit_text_userid_error)
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            githubViewModel.profileStateFlow.collect {
                when (it) {
                    is ResponseStates.Success -> {
                        setData(it.response)
                    }

                    is ResponseStates.Error -> {
                        showErrorDialog(getString(R.string.error_message))
                    }

                    is ResponseStates.Loading -> {
                    }

                    else -> {
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            githubViewModel.profileReposStateFlow.collect {
                when (it) {
                    is ResponseStates.Success -> {
                        setAdapter(it.response)
                    }

                    is ResponseStates.Error -> {
                        showErrorDialog(getString(R.string.fetching_error_message))
                    }

                    is ResponseStates.Loading -> {
                    }

                    else -> {
                    }
                }
            }
        }
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.title_error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.button_ok)) { dialogInterface, _ ->
                viewBinding.searchEditText.setText("")
                dialogInterface.dismiss()
            }.create()
        builder.show()
    }

    private fun setAdapter(response: List<ProfileRepo>) {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.item_animation)
        viewBinding.rvRepos.apply {
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            this.layoutManager = linearLayoutManager
            this.adapter = ReposAdapter(response) { onItemClick(it) }
            this.startAnimation(animation)
        }
    }

    private fun onItemClick(position: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ProfileDetailsFragment.newInstance(position))
            .addToBackStack(ProfileDetailsFragment.toString()).commit()
    }

    private fun setData(response: GithubProfile) {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.item_animation)
        Glide.with(requireActivity())
            .load(response.avatarUrl)
            .override(300, 300)
            .into(viewBinding.profileImage)
        viewBinding.profileImage.startAnimation(animation)
        viewBinding.profileName.text = response.name
        viewBinding.profileName.startAnimation(animation)
    }

    companion object {
        fun newInstance() = GithubProfileFragment()
            .apply {
                arguments = Bundle().apply {
                }
            }
    }
}