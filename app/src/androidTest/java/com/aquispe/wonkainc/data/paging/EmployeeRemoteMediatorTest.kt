package com.aquispe.wonkainc.data.paging

import androidx.annotation.VisibleForTesting
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.aquispe.wankainc.FakeEmployeesApi
import com.aquispe.wonkainc.data.local.database.EmployeesDatabase
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.domain.model.Favorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.aquispe.wonkainc.data.remote.model.EmployeeApiModel
import com.aquispe.wonkainc.data.remote.model.FavoriteApiModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
class EmployeeRemoteMediatorTest {

    private val fakeEmployee = EmployeeApiModel(
        id = 1,
        firstName = "Marcy",
        lastName = "Aquispe",
        favorite = FavoriteApiModel(
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

    private val mockDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        EmployeesDatabase::class.java
    ).fallbackToDestructiveMigration()
        .build()

    private val mockApi = FakeEmployeesApi()

    private val fakeEmployees =
        listOf(fakeEmployee, fakeEmployee.copy(id = 2), fakeEmployee.copy(id = 3))

    @After
    fun tearDown() {
        mockDb.clearAllTables()
        mockApi.failureMsg = null
        mockApi.clearEmployees()
    }

    @Test
    fun refresh_loadsMoreData_and_notEndOfPagination() = runTest {
        // Add mock results for the API to return.
        mockApi.addEmployees(fakeEmployees)

        val remoteMediator = EmployeeRemoteMediator(
            mockApi,
            mockDb
        )

        val pagingState = PagingState<Int, EmployeeDbModel>(
            listOf(),
            null,
            PagingConfig(25),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun given_LoadSuccess_with_noMoreData_when_refresh_then_endOfPaginationIsTrue() = runTest {
        // To test endOfPaginationReached, don't set up the mockApi to return post
        // data here.
        val remoteMediator =  EmployeeRemoteMediator(
            mockApi,
            mockDb
        )
        val pagingState = PagingState<Int, EmployeeDbModel>(
            listOf(),
            null,
            PagingConfig(25),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun given_errorOnRefresh_when_loadingData_then_ErrorResultIsReturned() = runTest {
        // Set up failure message to throw exception from the mock API.
        mockApi.failureMsg = "Throw test failure"

        val remoteMediator = EmployeeRemoteMediator(
            mockApi,
            mockDb
        )
        val pagingState = PagingState<Int, EmployeeDbModel>(
            listOf(),
            null,
            PagingConfig(25),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}

