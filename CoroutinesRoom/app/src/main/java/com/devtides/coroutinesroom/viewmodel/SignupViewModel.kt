package com.devtides.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.model.User
import com.devtides.coroutinesroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val database by lazy { UserDatabase(getApplication()).userDao() }

    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        coroutineScope.launch {
            val user = database.getUser(username)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = "User already exists"
                }
            } else {
                val newUser = User(username, password.hashCode(), info)
                val userId = database.insert(newUser)
                newUser.id = userId
                LoginState.login(newUser)
                withContext(Dispatchers.Main) {
                    signupComplete.value = true
                }
            }
        }
    }
}