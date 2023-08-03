package com.aquispe.wonkainc.data.remote.repository

import arrow.core.Either
import com.aquispe.wonkainc.data.remote.datasource.EmployeeDataSource
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.domain.repository.EmployeeRepository
import javax.inject.Inject

class DefaultEmployeeRepository @Inject constructor(
    private val employeeRemoteDataSource: EmployeeDataSource,
) : EmployeeRepository {

    override suspend fun getEmployeeById(id: Int): Either<Throwable, Employee> {
        return employeeRemoteDataSource.getEmployeeById(id)
    }
}
