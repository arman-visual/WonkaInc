package com.aquispe.wonkainc.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel

@Dao
interface EmployeeDbModelDao {

    @Query("SELECT * FROM employeeDbModel")
    fun getEmployees(): PagingSource<Int, EmployeeDbModel>

    @Query(
        "SELECT *\n" +
            "FROM employeeDbModel\n" +
            "WHERE\n" +
            "  CASE\n" +
            "    WHEN gender =:gender IS NULL AND profession =:profession IS NULL THEN 1\n" +
            "    WHEN gender =:gender IS NOT NULL AND profession =:profession IS NOT NULL THEN gender = :gender AND profession = :profession\n" +
            "    WHEN gender =:gender IS NOT NULL OR profession =:profession IS NOT NULL THEN gender = :gender OR profession = :profession\n" +
            "  END\n" +
            "ORDER BY id ASC\n"
    )
    fun getFilteredEmployees(gender: String?, profession: String?): List<EmployeeDbModel>

    @Query("SELECT COUNT(id) FROM employeeDbModel")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(characters: List<EmployeeDbModel>)

    @Query("DELETE FROM employeeDbModel")
    fun deleteAllCharacters()
}
