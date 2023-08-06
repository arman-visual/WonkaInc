package com.aquispe.wonkainc.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquispe.wonkainc.di.Main
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.usecases.GetEmployeeIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(
    private val getEmployeeIdUseCase: GetEmployeeIdUseCase,
    @Main private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private var _stateDetailView = MutableStateFlow<ViewState>(ViewState.Loading)
    val stateDetailView: StateFlow<ViewState> get() = _stateDetailView.asStateFlow()

    fun getEmployeeId(id: Int) {
        viewModelScope.launch(dispatcher) {
            getEmployeeIdUseCase(id).fold(
                ifLeft = {
                    _stateDetailView.value = ViewState.Error(it)
                },
                ifRight = {
                    _stateDetailView.value = ViewState.Content(it)
                })

        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val employee: Employee) : ViewState()
    }
}
