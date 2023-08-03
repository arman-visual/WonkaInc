package com.aquispe.wonkainc.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.aquispe.wonkainc.data.local.database.EmployeesDatabase
import com.aquispe.wonkainc.data.local.database.entities.EmployeeDbModel
import com.aquispe.wonkainc.data.local.database.entities.EmployeeRemoteKeys
import com.aquispe.wonkainc.data.remote.mapper.toDbModel
import com.aquispe.wonkainc.data.remote.model.EmployeeApiModel
import com.aquispe.wonkainc.data.remote.service.EmployeeService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class EmployeeRemoteMediator @Inject constructor(
    private val employeeService: EmployeeService,
    private val employeeDatabase: EmployeesDatabase,
) : RemoteMediator<Int, EmployeeDbModel>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (employeeDatabase.remoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EmployeeDbModel>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys: EmployeeRemoteKeys? = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys: EmployeeRemoteKeys? = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse =
                employeeService.getEmployeesByPage(page = page)

            val employees = apiResponse.results
            val endOfPaginationReached = employees.isEmpty()

            employeeDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    clearDatabase()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys: List<EmployeeRemoteKeys> = employees.map { employeeApiModel ->
                    EmployeeRemoteKeys(
                        id = employeeApiModel.id,
                        prevPage = prevKey,
                        nextPage = nextKey,
                        currentPage = page
                    )
                }

                saveInDataBase(remoteKeys, employees)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun clearDatabase() {
        employeeDatabase.remoteKeysDao().clearRemoteKeys()
        employeeDatabase.employeeDbModelDao().deleteAllCharacters()
    }

    private suspend fun saveInDataBase(
        remoteKeys: List<EmployeeRemoteKeys>,
        employees: List<EmployeeApiModel>,
    ) {
        employeeDatabase.remoteKeysDao().addAllRemoteKeys(remoteKeys)
        employeeDatabase.employeeDbModelDao()
            .insertEmployees(employees.map { it.toDbModel() })
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, EmployeeDbModel>): EmployeeRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                employeeDatabase.remoteKeysDao().getRemoteKeyById(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, EmployeeDbModel>): EmployeeRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { characterDbModel ->
            employeeDatabase.remoteKeysDao().getRemoteKeyById(characterDbModel.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, EmployeeDbModel>): EmployeeRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { characterDbModel ->
            employeeDatabase.remoteKeysDao().getRemoteKeyById(characterDbModel.id)
        }
    }
}
