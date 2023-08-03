package com.aquispe.wonkainc.data.remote.datasource

import arrow.core.Either
import com.aquispe.wonkainc.domain.model.Employee

interface EmployeeDataSource {
    suspend fun getEmployeeById(id: Int): Either<Throwable, Employee>
}
