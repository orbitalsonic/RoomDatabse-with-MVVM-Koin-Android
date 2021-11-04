package com.orbitalsonic.roomdatabasemvvmkoin

import android.app.Application
import com.orbitalsonic.roomdatabasemvvmkoin.database.UserDatabase
import com.orbitalsonic.roomdatabasemvvmkoin.repository.UserRepository
import com.orbitalsonic.roomdatabasemvvmkoin.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { UserDatabase.getDatabase(this,applicationScope) }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MainApplication)
            modules(listOf(appModule))
        }
    }

    private val appModule = module{


        single { database.userDao() }

        single { UserRepository(get()) }

        viewModel { UserViewModel(get()) }


    }
}