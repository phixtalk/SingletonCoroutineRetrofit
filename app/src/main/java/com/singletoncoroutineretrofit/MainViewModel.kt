package com.singletoncoroutineretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.singletoncoroutineretrofit.models.User
import com.singletoncoroutineretrofit.repository.Repository

class MainViewModel: ViewModel() {
    //no need to create a singleton here, because it will be bond to the activity

    private val _userId: MutableLiveData<String> = MutableLiveData()

    //the transformation.switchmap method is simply observing the MutableLiveData object(_userId)
    //so that whenever it changes, it will trigger (call and execute) the Repository.getUser(userId)
    //and the _userId can be changed or updated by calling the setUserId()
    //transformation.switchmap method takes in a string and returns a User object, hence the name switchMap
    val user: LiveData<User> = Transformations.switchMap(_userId){userId ->
            Repository.getUser(userId)
        }

    //this is the trigger that calls the above switchmap method
    //and this trigger is pulled from the mainactivity when the user makes a request with a particular userid value
    //once the _userId.value changes, the switchmap method is trigger
    fun setUserId(userId: String){
        val update = userId
        if(_userId.value == update){
            return
        }
        _userId.value = update//here the value of the _userId object has been updated
    }

    fun cancelJobs(){
        Repository.cancelJobs()
    }

}