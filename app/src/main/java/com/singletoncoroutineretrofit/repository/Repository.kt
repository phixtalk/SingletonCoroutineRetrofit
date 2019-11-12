package com.singletoncoroutineretrofit.repository

import androidx.lifecycle.LiveData
import com.singletoncoroutineretrofit.api.MyRetrofitBuilder
import com.singletoncoroutineretrofit.models.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job : CompletableJob? = null

    fun getUser(userId: String): LiveData<User>{

        job = Job()//this is not necessary, but gives us control so we can create specific jobs to run our coroutines
        //rather than just having them run free on a displatcher, io, main or default

        return object: LiveData<User>(){//return a new live data user object
            override fun onActive() {
                super.onActive()
                job?.let {theJob ->
                    //add the job to the IO scope, to create a unique coroutine on the background thread thats referencing theJob
                    CoroutineScope(IO + theJob).launch {
                        //call retrofit api service to get data remotely
                        val user = MyRetrofitBuilder.apiService.getUser(userId)
                        //once we have the date, to update the ui, we need to switch to the main thread
                        withContext(Main){
                            value = user
                            //finally, mark job as completed
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }

}
