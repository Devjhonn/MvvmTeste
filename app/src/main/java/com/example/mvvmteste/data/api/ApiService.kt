package com.example.mvvmteste.data.api

import com.example.mvvmteste.data.model.User
import io.reactivex.Single

interface ApiService {
    fun getUsers(): Single<List<User>>

}