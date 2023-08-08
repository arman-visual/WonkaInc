package com.aquispe.wonkainc.usecases

import arrow.core.left
import arrow.core.right
import com.aquispe.wonkainc.domain.model.Favorite
import com.aquispe.wonkainc.domain.repository.EmployeeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetEmployeeIdUseCaseTest {

    @MockK
    private lateinit var repository: EmployeeRepository

    private lateinit var getEmployeeIdUseCase: GetEmployeeIdUseCase

    private val fakeEmployee = com.aquispe.wonkainc.domain.model.Employee(
        id = 1,
        firstName = "Marcy",
        lastName = "Aquispe",
        favorite = Favorite(
            color = "red",
            food = "pizza",
            randomString = "random",
            song = "rock"
        ),
        gender = "F",
        image = "",
        profession = "",
        email = "",
        age = 0,
        country = "",
        height = 99
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getEmployeeIdUseCase = GetEmployeeIdUseCase(repository)
    }

    @Test
    fun `GIVEN a id WHEN invoke THEN return a success response`() = runBlocking {
        // Given
        val id = 1
        val responseExpected = fakeEmployee.right()
        coEvery { repository.getEmployeeById(id) } returns responseExpected
        // When
        val result = getEmployeeIdUseCase(id)

        // Then
        coVerify(exactly = 1) { repository.getEmployeeById(id) }
        assert(result.isRight())
    }

    @Test
    fun `GIVEN a bad response WHEN invoke THEN return a exception response`() = runBlocking {
        // Given
        val id = 145992
        val responseExpected = Throwable("Error").left()
        coEvery { repository.getEmployeeById(id) } returns responseExpected
        // When
        val result = getEmployeeIdUseCase(id)

        // Then
        coVerify(exactly = 1) { repository.getEmployeeById(id) }
        assertEquals(responseExpected, result)
        assert(result.isLeft())
    }

}
