package com.aquispe.wonkainc.data.remote.repository

import androidx.paging.Pager
import androidx.paging.map
import androidx.paging.PagingData
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.local.mapper.toDomain
import com.aquispe.wonkainc.domain.model.Employee
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val pager: Pager<Int, EmployeeDbModel>,
) {

    fun getAllEmployees(): Flow<PagingData<Employee>> =
        pager.flow.map { pagingData ->
            pagingData.map { employeeDbModel ->
                employeeDbModel.toDomain()
            }
        }

}
