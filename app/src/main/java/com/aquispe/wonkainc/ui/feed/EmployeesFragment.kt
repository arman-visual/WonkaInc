package com.aquispe.wonkainc.ui.feed

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.aquispe.wonkainc.databinding.FragmentEmployeesBinding
import com.aquispe.wonkainc.ui.feed.adapter.EmployeeAdapter
import com.aquispe.wonkainc.ui.util.getMessage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private val employeesViewModel: EmployeesViewModel by viewModels()
    private lateinit var adapter: EmployeeAdapter
    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EmployeeAdapter {
            onClickDetail(it.id)
        }

        binding.rcvEmployees.adapter = adapter

        alertDialog = FilterDialog(
            requireActivity(),
            onClickApplyFilter = { gender, profession ->
                onApplyFilter(
                    gender,
                    profession
                )
            }).create()

        binding.includeHeader.ivFilters.setOnClickListener {
            alertDialog.show()
        }
        subscribe()
        employeesViewModel.getEmployeesGeneral()
    }

    private fun onApplyFilter(gender: String?, profession: String?) {
        employeesViewModel.updateGenderAndProfessionFilter(gender, profession)
        binding.rcvEmployees.scrollToPosition(0)
    }

    private fun subscribe() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                employeesViewModel.searchStateUi.collectLatest {
                    adapter.submitData(it.pagingData.value)
                }
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .collect { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            //TODO show loading
                        }
                        is LoadState.NotLoading -> {
                            if (adapter.snapshot().isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "No hay resultados disponibles.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        is LoadState.Error -> {
                            val errorState = (loadState.refresh as LoadState.Error).error
                            handleError(errorState)
                        }
                    }
                }
        }
    }

    private fun handleError(errorState: Throwable) {
        Snackbar.make(binding.root, errorState.getMessage(), Snackbar.LENGTH_LONG).show()
    }

    private fun onClickDetail(id: Int) {
        val action = EmployeesFragmentDirections.actionEmployeesFragmentToEmployeeDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
        super.onDestroyView()
    }
    //Mejorar diseño tanto del item como del detalle
    //Crear los test unitarios y Readme
}
