package br.com.raynerweb.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.databinding.FragmentWelcomeBinding
import br.com.raynerweb.pokemon.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadingState.observe(viewLifecycleOwner, {
            if (it) {
                binding.btnNext.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
                binding.tvProgress.visibility = View.VISIBLE
            } else {
                binding.tvProgress.visibility = View.GONE
                binding.progress.visibility = View.GONE
                binding.btnNext.visibility = View.VISIBLE
            }
        })
    }

    fun next(view: View) {
        findNavController().navigate(R.id.action_welcomeFragment_to_usernameFragment)
    }

}