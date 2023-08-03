package com.aquispe.wonkainc.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aquispe.wonkainc.data.local.database.EmployeesDatabase
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.paging.EmployeeRemoteMediator
import com.aquispe.wonkainc.data.remote.service.EmployeeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCharactersPager(
        employeeDb: EmployeesDatabase,
        employeeApiService: EmployeeService
    ): Pager<Int, EmployeeDbModel> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = EmployeeRemoteMediator(
                employeeService = employeeApiService,
                employeeDatabase = employeeDb,
            ),
            pagingSourceFactory = {
                employeeDb.employeeDbModelDao().getEmployees()
            }
        )
    }
}

private const val PAGE_SIZE = 10
