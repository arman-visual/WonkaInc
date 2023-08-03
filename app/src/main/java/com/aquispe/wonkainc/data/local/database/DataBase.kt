package com.aquispe.wonkainc.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.local.database.entities.EmployeeRemoteKeys

@Database(
    entities = [
        EmployeeDbModel::class,
        EmployeeRemoteKeys::class
    ],
    version = 1,
)

@TypeConverters(Converters::class)
abstract class EmployeesDatabase : RoomDatabase() {
    abstract fun employeeDbModelDao(): EmployeeDbModelDao
    abstract fun remoteKeysDao(): EmployeeRemoteKeysDao
}
