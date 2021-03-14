package br.com.raynerweb.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.databinding.FragmentUsernameBinding
import br.com.raynerweb.pokemon.viewmodel.UsernameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsernameFragment : Fragment() {

    private lateinit var binding: FragmentUsernameBinding

    private val viewModel: UsernameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsernameBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.usernameSaved.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_usernameFragment_to_pokemonTypeFragment)
        })

        viewModel.usernameError.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), R.string.please_enter_yout_name, Toast.LENGTH_LONG)
                .show()
        })

    }

    fun next(view: View) {
        viewModel.saveUsername()
    }

}