package com.example.retocji.di

import android.content.Context
import android.content.SharedPreferences
import com.example.retocji.constants.URLConstants.Companion.API_URL
import com.example.retocji.data.repositories.PDFRepositoryImpl
import com.example.retocji.data.sources.local.PDFGenerator
import com.example.retocji.data.sources.remote.ApiService
import com.example.retocji.domain.repositories.PDFRepository
import com.example.retocji.domain.usescases.GenerarPDFUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

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
