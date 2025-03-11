package kz.sdu.edu.di

import android.content.Context
import android.content.SharedPreferences
import kz.sdu.edu.ViewModel.AuthViewModel
import kz.sdu.edu.ViewModel.GenreViewModel
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.repository.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    single { ApiService.create() }
    single { AuthRepository(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { GenreViewModel(get()) }


}