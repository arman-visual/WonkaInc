package com.aquispe.wonkainc.data.remote.service

import com.aquispe.wonkainc.data.remote.model.EmployeeApiModel
import com.aquispe.wonkainc.data.remote.model.EmployeesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EmployeeService {

    @GET("oompa-loompas/{id}")
    suspend fun getEmployeeById(
        @Path("id")
        id: Int,
    ): EmployeeApiModel

    @GET("oompa-loompas")
    suspend fun getEmployeesByPage(
        @Query("page")
        page: Int,
    ): EmployeesResponse

}
