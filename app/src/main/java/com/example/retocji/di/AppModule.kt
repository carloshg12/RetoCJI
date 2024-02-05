package com.example.retocji.di

import android.content.Context
import android.content.SharedPreferences
import com.example.retocji.data.datasource.local.PDFGenerator
import com.example.retocji.data.repositories.PDFRepository
import com.example.retocji.data.repositories.PDFRepositoryImpl
import com.example.retocji.domain.repositories.ApiService
import com.example.retocji.domain.repositories.RetrofitInstance
import com.example.retocji.domain.usescases.GenerarPDFUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitInstance.api
    }

    @Singleton
    @Provides
    fun providePDFGenerator(): PDFGenerator = PDFGenerator()

    @Singleton
    @Provides
    fun providePDFRepository(pdfGenerator: PDFGenerator): PDFRepository =
        PDFRepositoryImpl(pdfGenerator)

    @Singleton
    @Provides
    fun provideGenerarPDFUseCase(pdfRepository: PDFRepository): GenerarPDFUseCase =
        GenerarPDFUseCase(pdfRepository)

}
