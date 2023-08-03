package com.aquispe.wonkainc.usecases

import arrow.core.Either
import com.aquispe.wonkainc.domain.model.Employee
import com.aquispe.wonkainc.domain.repository.EmployeeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEmployeeIdUseCase @Inject constructor(
    private val repository: EmployeeRepository,
) {
    suspend operator fun invoke(id: Int): Either<Throwable, Employee> =
        repository.getEmployeeById(id)
}
