package com.aquispe.wonkainc.domain.repository

import arrow.core.Either
import com.aquispe.wonkainc.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployeeById(id: Int): Either<Throwable, Employee>
}
