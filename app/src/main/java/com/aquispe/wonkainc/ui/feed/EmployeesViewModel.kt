package com.aquispe.wonkainc.ui.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aquispe.wonkainc.data.remote.repository.PagingRepository
import com.aquispe.wonkainc.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val pagingRepository: PagingRepository
) : ViewModel() {

    private var _searchStateUi = MutableStateFlow(SearchStateUi())
    val searchStateUi = _searchStateUi.asStateFlow()

    fun getEmployeesGeneral(query: String = EMPTY_QUERY) {

        viewModelScope.launch {
            if (query.isNotEmpty()) {
                val filteredEmployees = pagingRepository.getFilteredEmployees(query)
                _searchStateUi.value =
                    _searchStateUi.value.copy(
                        pagingData = MutableStateFlow(
                            PagingData.from(
                                filteredEmployees
                            )
                        )
                    )
            } else {
                pagingRepository.getAllEmployees()
                    .cachedIn(viewModelScope)
                    .collect {
                        _searchStateUi.value =
                            _searchStateUi.value.copy(
                                pagingData = MutableStateFlow(
                                    it
                                )
                            )
                    }
            }
        }
    }

    fun updateQueryFilter(query: String?) {
        if (query != null) {
            _searchStateUi.value = _searchStateUi.value.copy(searchQuery = query)
            getEmployeesGeneral(query)
        }
    }

    companion object {
        private const val EMPTY_QUERY = ""
    }
}

data class SearchStateUi(
    val searchQuery: String = "",
    val professionFilter: String = "",
    val genderFilter: String = "",
    val pagingData: StateFlow<PagingData<Employee>> = MutableStateFlow(PagingData.empty()),
)
