package com.example.mvvmteste.data.repository

import com.example.mvvmteste.data.api.ApiHelper
import com.example.mvvmteste.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {
    fun getUsers(): Single<List<User>>{
        return apiHelper.getUsers()
    }
}