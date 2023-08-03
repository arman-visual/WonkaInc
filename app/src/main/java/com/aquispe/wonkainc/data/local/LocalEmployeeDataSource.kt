package com.aquispe.wonkainc.data.local

import com.aquispe.wonkainc.data.local.database.EmployeesDatabase
import javax.inject.Inject

class LocalEmployeeDataSource @Inject constructor(
    employeeDbModelDao: EmployeesDatabase
) {

}
