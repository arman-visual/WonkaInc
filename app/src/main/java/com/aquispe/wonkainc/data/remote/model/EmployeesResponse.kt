package com.aquispe.wonkainc.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeesResponse(
    val current: Int,
    val results: List<EmployeeApiModel>,
    val total: Int
)
