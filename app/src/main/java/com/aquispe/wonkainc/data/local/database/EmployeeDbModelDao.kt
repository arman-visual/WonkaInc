package com.aquispe.wonkainc.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDbModelDao {

    @Query("SELECT * FROM employeeDbModel")
    fun getEmployees(): PagingSource<Int, EmployeeDbModel>

    @Query("SELECT * FROM employeeDbModel WHERE firstName  = :firstName ORDER BY id ASC")
    fun getFilteredEmployees(firstName: String): List<EmployeeDbModel>

    @Query("SELECT COUNT(id) FROM employeeDbModel")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(characters: List<EmployeeDbModel>)

    @Query("DELETE FROM employeeDbModel")
    fun deleteAllCharacters()
}
