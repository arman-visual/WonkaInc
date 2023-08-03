package com.aquispe.wonkainc.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aquispe.wonkainc.databinding.FragmentEmployeeDetailBinding
import com.aquispe.wonkainc.ui.util.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeDetailFragment : Fragment() {

    private val viewModel: EmployeeDetailViewModel by viewModels()
    private var _binding: FragmentEmployeeDetailBinding? = null
    private val args: EmployeeDetailFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeDetailBinding.inflate(inflater, container, false)
        id = args.id
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        viewModel.getEmployeeId(requireNotNull(id))
    }

    private fun subscribe() {
        viewLifecycleOwner.launchAndCollect(viewModel.employee) {
            binding.tvName.text = it?.firstName
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
