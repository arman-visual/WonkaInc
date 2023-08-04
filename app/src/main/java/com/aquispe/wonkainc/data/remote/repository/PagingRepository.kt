package com.aquispe.wonkainc.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.aquispe.wonkainc.data.local.database.EmployeesDatabase
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.local.mapper.toDomain
import com.aquispe.wonkainc.domain.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val pager: Pager<Int, EmployeeDbModel>,
    private val employeeDb: EmployeesDatabase,
) {

    fun getAllEmployees(): Flow<PagingData<Employee>> =
        pager.flow.map { pagingData ->
            pagingData.map { employeeDbModel ->
                employeeDbModel.toDomain()
            }
        }

    suspend fun getFilteredEmployees(query: String): List<Employee> =
        withContext(Dispatchers.IO) {
            employeeDb.employeeDbModelDao().getFilteredEmployees(query).map { employeeDbModel ->
                employeeDbModel.toDomain()
            }
        }

}
