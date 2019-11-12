package com.singletoncoroutineretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //next we observer the user object
        viewModel.user.observe(this, Observer {user->
            println("DEBUG: ${user}")
        })

        //here we simple update the userid in other to trigger the transformation.switchmap method
        viewModel.setUserId("1")

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

}
