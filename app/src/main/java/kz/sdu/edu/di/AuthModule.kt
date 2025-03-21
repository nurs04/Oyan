package kz.sdu.edu.di

import android.content.Context
import android.content.SharedPreferences
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.ViewModel.GenreViewModel
import kz.sdu.edu.repository.AuthRepository
import kz.sdu.edu.repository.AuthRepositoryImpl
import kz.sdu.edu.repository.GenreRepository
import kz.sdu.edu.repository.GenreRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<GenreRepository> { GenreRepositoryImpl(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { GenreViewModel(get()) }
}