package kz.sdu.edu.di

import androidx.room.Room
import kz.sdu.edu.ViewModel.MainViewModel
import kz.sdu.edu.ViewModel.SeeAllViewModel
import kz.sdu.edu.data.ApiService
import kz.sdu.edu.data.AppDatabase
import kz.sdu.edu.repository.MainRepository
import kz.sdu.edu.repository.MainRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single{
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "my_books.db"
        ).build()
    }
    single { get<AppDatabase>().bookDao() }
    single<MainRepository> {MainRepositoryImpl(get(), get())}
    viewModel {MainViewModel(get()) }
    viewModel {SeeAllViewModel(get())}

}