package com.example.arcanittest.app.di

import com.example.arcanittest.data.ReposRepositoryImpl
import com.example.arcanittest.data.UsersRepositoryImpl
import com.example.arcanittest.domain.repository.ReposRepository
import com.example.arcanittest.domain.repository.UsersRepository
import org.koin.dsl.module

val dataModule = module {

    single<UsersRepository> {
        UsersRepositoryImpl(usersService = get())
    }

    single<ReposRepository> {
        ReposRepositoryImpl(reposService = get())
    }
}