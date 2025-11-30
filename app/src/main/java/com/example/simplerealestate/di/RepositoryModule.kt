package com.example.simplerealestate.di

import com.example.simplerealestate.data.repository.PropertyRepositoryImpl
import com.example.simplerealestate.domain.repository.PropertyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPropertyRepository(
        impl: PropertyRepositoryImpl
    ): PropertyRepository
}