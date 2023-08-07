package com.aquispe.wonkainc.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
                    binding.clEmployeeDetailContainer.visibility = View.GONE
                    binding.pbLoadingDetail.visibility = View.VISIBLE
                }
                is EmployeeDetailViewModel.ViewState.Content -> {
                    binding.pbLoadingDetail.visibility = View.GONE
                    binding.clEmployeeDetailContainer.visibility = View.VISIBLE
                    showDetailProfile(stateView.employee)
                }
                is EmployeeDetailViewModel.ViewState.Error -> {
                    binding.pbLoadingDetail.visibility = View.GONE
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

            when (val color = employee.favorite.color) {
                "red" -> {
                    val colour = ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                    showColorFavorite(colour, color)
                }
                "blue" -> {
                    val colour = ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                    showColorFavorite(colour, color)
                }
            }

            binding.includeFavoriteProfile.tvFavoriteFood.text = employee.favorite.food

            binding.includeInformationProfile.tvProfession.text = employee.profession
            binding.includeInformationProfile.tvCountry.text = employee.country
            binding.includeInformationProfile.tvAge.text = employee.age.toString()

            binding.includeSecondInformationProfile.tvHeight.text = employee.height.toString()
            binding.includeSecondInformationProfile.tvIndentify.text = requireNotNull(id).toString()
        }
    }

    private fun showColorFavorite(colour: Int, color: String) {
        binding.includeFavoriteProfile.tvFavoriteColour.setTextColor(
            colour
        )
        binding.includeFavoriteProfile.ivFavoriteColour.setColorFilter(colour)
        binding.includeFavoriteProfile.tvFavoriteColour.text = color
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
