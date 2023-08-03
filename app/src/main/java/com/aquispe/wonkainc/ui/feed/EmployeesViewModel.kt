package com.aquispe.wonkainc.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aquispe.wonkainc.data.remote.repository.PagingRepository
import com.aquispe.wonkainc.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    pagingRepository: PagingRepository
) : ViewModel() {
    val employees: Flow<PagingData<Employee>> =
        pagingRepository.getAllEmployees().cachedIn(viewModelScope)
}
