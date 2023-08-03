package com.aquispe.wonkainc.data.remote.datasource

import arrow.core.Either
import com.aquispe.wonkainc.data.remote.mapper.toDomain
import com.aquispe.wonkainc.data.remote.service.EmployeeService
import com.aquispe.wonkainc.domain.model.Employee
import javax.inject.Inject

class RemoteEmployeeDataSource @Inject constructor(
    private val employeeService: EmployeeService,
) : EmployeeDataSource {

    override suspend fun getEmployeeById(id: Int): Either<Throwable, Employee> {
        return Either.catch {
            employeeService.getEmployeeById(id).toDomain()
        }
    }
}
