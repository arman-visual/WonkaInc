package com.aquispe.wonkainc.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.usecases.GetEmployeeIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(private val getEmployeeIdUseCase: GetEmployeeIdUseCase) :
    ViewModel() {

    private var _employee = MutableStateFlow<Employee?>(null)
    val employee: StateFlow<Employee?> get() = _employee.asStateFlow()

    private var _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> get() = _error.asStateFlow()

    fun getEmployeeId(id: Int) {
        viewModelScope.launch {
            getEmployeeIdUseCase(id).fold(
                ifLeft = { Log.w("EmployeeDetailViewModel", "getEmployeeId: ${it.message}") },
                ifRight = {
                    _employee.value = it
                    Log.w("EmployeeDetailViewModel", "getEmployeeId: ${it.firstName}")
                })

        }
    }

}
