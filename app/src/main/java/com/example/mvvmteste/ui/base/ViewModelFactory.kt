package com.example.mvvmteste.ui.base


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmteste.data.api.ApiHelper
import com.example.mvvmteste.data.repository.MainRepository
import com.example.mvvmteste.ui.main.viewModel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Nome da Classe não encontrado")
    }

}