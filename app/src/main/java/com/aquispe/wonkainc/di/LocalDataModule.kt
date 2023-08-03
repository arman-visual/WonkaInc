package com.aquispe.wonkainc.di

import android.app.Application
import androidx.room.Room
import com.aquispe.wonkainc.data.local.LocalEmployeeDataSource
import com.aquispe.wonkainc.data.local.database.EmployeesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun employeeDataBaseProvider(application: Application): EmployeesDatabase =
        Room.databaseBuilder(
            application,
            EmployeesDatabase::class.java,
            "employees_db"
        ).build()

    @Provides
    fun provideLocalEmployeeDataSource(
        employeesDatabase: EmployeesDatabase,
    ): LocalEmployeeDataSource = LocalEmployeeDataSource(employeesDatabase)

}
