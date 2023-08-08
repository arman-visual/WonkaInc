package com.aquispe.wonkainc.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquispe.wonkainc.data.local.database.entities.EmployeeRemoteKeys

@Dao
interface EmployeeRemoteKeysDao {

    @Query("SELECT * FROM employee_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyById(id: Int): EmployeeRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<EmployeeRemoteKeys>)

    @Query("DELETE FROM employee_remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM employee_remote_keys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
