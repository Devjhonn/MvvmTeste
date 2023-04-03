package com.example.mvvmteste.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmteste.R
import com.example.mvvmteste.data.api.ApiHelper
import com.example.mvvmteste.data.api.ApiServiceImpl
import com.example.mvvmteste.data.model.User
import com.example.mvvmteste.ui.base.ViewModelFactory
import com.example.mvvmteste.ui.main.adapter.MainAdapter
import com.example.mvvmteste.ui.main.viewModel.MainViewModel
import com.example.mvvmteste.utils.Status

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver(){
        mainViewModel.getUsers().observe(this, Observer{
            when(it.status){

                Status.SUCCESS ->{
                    val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                    progressBar.visibility = View.GONE
                    it.data?.let{users -> renderList(users)}
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.visibility = View.VISIBLE
                }

                Status.LOADING ->{
                    val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                Status.ERROR ->{
                    val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}