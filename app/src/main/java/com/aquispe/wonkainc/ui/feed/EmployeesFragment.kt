package com.aquispe.wonkainc.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.aquispe.wonkainc.databinding.FragmentEmployeesBinding
import com.aquispe.wonkainc.ui.feed.adapter.EmployeeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private val employeesViewModel: EmployeesViewModel by viewModels()
    private lateinit var adapter: EmployeeAdapter
    private lateinit var textChangeCountDownJob: Job
    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!

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
        binding.includeHeader.wdgSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (::textChangeCountDownJob.isInitialized)
                    textChangeCountDownJob.cancel()

                textChangeCountDownJob = lifecycleScope.launch {
                    delay(1000)
                    employeesViewModel.updateQueryFilter(query)
                }
                if (binding.includeHeader.tvCancel.visibility == View.GONE)
                    binding.includeHeader.tvCancel.visibility = View.VISIBLE
                return true
            }
        })
        subscribe()
        employeesViewModel.getEmployeesGeneral()
    }

    private fun subscribe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                employeesViewModel.searchStateUi.collectLatest {
                    adapter.submitData(it.pagingData.value)
                }
            }
        }
    }

    private fun onClickDetail(id: Int) {
        val action = EmployeesFragmentDirections.actionEmployeesFragmentToEmployeeDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
