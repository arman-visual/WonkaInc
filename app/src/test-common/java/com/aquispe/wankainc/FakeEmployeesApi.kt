package com.aquispe.wankainc

import com.aquispe.wonkainc.data.remote.model.EmployeeApiModel
import com.aquispe.wonkainc.data.remote.model.EmployeesResponse
import com.aquispe.wonkainc.data.remote.service.EmployeeService
import java.io.IOException

class FakeEmployeesApi : EmployeeService {

    private val searchCharacters = mutableListOf<EmployeeApiModel>()
    private val allCharacters = mutableListOf<EmployeeApiModel>()
    var failureMsg: String? = null

    fun addEmployees(employees: List<EmployeeApiModel>) {
        this.searchCharacters.addAll(employees)
        this.allCharacters.addAll(employees)
    }

    fun clearEmployees() {
        searchCharacters.clear()
        allCharacters.clear()
    }

    override suspend fun getEmployeeById(id: Int): EmployeeApiModel {
        return searchCharacters.first { it.id == id }
    }

    override suspend fun getEmployeesByPage(page: Int): EmployeesResponse {
        failureMsg?.let {
            throw IOException(it)
        }

        return EmployeesResponse(
            current = 1,
            results = allCharacters,
            total = 1
        )
    }

}
