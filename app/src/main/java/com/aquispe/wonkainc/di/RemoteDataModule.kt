package com.aquispe.wonkainc.di

import com.aquispe.wonkainc.data.remote.client.APIClient
import com.aquispe.wonkainc.data.remote.datasource.EmployeeDataSource
import com.aquispe.wonkainc.data.remote.repository.DefaultEmployeeRepository
import com.aquispe.wonkainc.data.remote.service.EmployeeService
import com.aquispe.wonkainc.domain.repository.EmployeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideHttpClient(): OkHttpClient = APIClient.getHttpClient()

    @Provides
    @Singleton
    @BaseUrl
    fun provideUrl(): String = BASE_URL

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideContentService(
        retrofit: Retrofit,
    ): EmployeeService = retrofit.create(EmployeeService::class.java)

    @Provides
    @Singleton
    fun provideEmployeeRepository(
        remoteEmployeeDataSource: EmployeeDataSource,
    ): EmployeeRepository = DefaultEmployeeRepository(remoteEmployeeDataSource)
}

private const val BASE_URL = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/"
