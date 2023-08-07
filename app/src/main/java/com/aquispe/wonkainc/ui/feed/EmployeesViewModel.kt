package com.aquispe.wonkainc.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aquispe.wonkainc.data.remote.repository.PagingRepository
import com.aquispe.wonkainc.di.Main
import com.aquispe.wonkainc.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val pagingRepository: PagingRepository,
    @Main private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _searchStateUi = MutableStateFlow(SearchStateUi())
    val searchStateUi = _searchStateUi.asStateFlow()

    init {
        getEmployeesGeneral()
    }

    fun getEmployeesGeneral(
        gender: String? = null,
        profession: String? = null
    ) {

        viewModelScope.launch(dispatcher) {

            val isDisableGenderFilter = _searchStateUi.value.genderFilter.isNullOrEmpty()
            val isDisableProfessionFilter = _searchStateUi.value.professionFilter.isNullOrEmpty()

            if (!isDisableGenderFilter ||!isDisableProfessionFilter) {
                val filteredEmployees =
                    pagingRepository.getFilteredEmployees(gender, profession)
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

    fun updateGenderAndProfessionFilter(gender: String?, profession: String?) {
        if (gender == null && profession == null) {
            _searchStateUi.value = _searchStateUi.value.copy(genderFilter = null, professionFilter = null)
            getEmployeesGeneral()
        } else {
            _searchStateUi.value = _searchStateUi.value.copy(genderFilter = gender, professionFilter = profession)
            getEmployeesGeneral(gender, profession)
        }
    }

    data class SearchStateUi(
        val professionFilter: String? = null,
        val genderFilter: String? = null,
        val pagingData: StateFlow<PagingData<Employee>> = MutableStateFlow(PagingData.empty()),
    )
}


