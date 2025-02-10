package com.example.apiboutiqueexame.room.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.apiboutiqueexame.room.User
import com.example.apiboutiqueexame.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    var user = userDao.getUserLoggedFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            null
        )
    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(User(email = email, password = password))
        }
    }



    fun getUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUser(email, password) // Agora retorna um User diretamente
            if (user != null) {
                userDao.updateUserLoggedStatus(user.id, true)
            }
        }
    }

}



class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

