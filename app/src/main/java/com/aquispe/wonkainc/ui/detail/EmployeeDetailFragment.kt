package com.aquispe.wonkainc.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aquispe.wonkainc.R
import com.aquispe.wonkainc.databinding.FragmentEmployeeDetailBinding
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.ui.util.getMessage
import com.aquispe.wonkainc.ui.util.launchAndCollect
import com.aquispe.wonkainc.ui.util.loadUrlWithCircleCrop
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
        viewLifecycleOwner.launchAndCollect(viewModel.stateDetailView) { stateView ->
            when (stateView) {
                is EmployeeDetailViewModel.ViewState.Loading -> {
                   Log.d("TAG", "Loading")//TODO aquispe añadir loading
                }
                is EmployeeDetailViewModel.ViewState.Content -> {
                    showDetailProfile(stateView.employee)
                }
                is EmployeeDetailViewModel.ViewState.Error -> {
                    binding.includeError.clErrorContainer.visibility = View.VISIBLE
                    binding.includeError.tvErrorMessage.text = stateView.throwable.getMessage(binding.root.context)

                    binding.clEmployeeDetailContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun showDetailProfile(employee: Employee?) {
        if (employee != null) {
            binding.tvFullName.text = buildString {
                append(employee.firstName)
                append(" ")
                append(employee.lastName)
            }

            binding.tvEmail.text = employee.email

            if (employee.image.isNotEmpty())
                binding.circleProfileImage.loadUrlWithCircleCrop(employee.image)
            else
                binding.circleProfileImage.setImageResource(R.drawable.profile_icon)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
