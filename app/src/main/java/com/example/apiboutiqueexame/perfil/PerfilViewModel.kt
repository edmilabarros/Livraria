package com.example.apiboutiqueexame.perfil

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.apiboutiqueexame.api.ApiService
import com.example.apiboutiqueexame.api.Livro
import com.example.apiboutiqueexame.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val apiService: ApiService,
    private val userDao: UserDao
): ViewModel() {

    var user = userDao.getUserLoggedFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            null
        )
    val livros = mutableStateOf<List<Livro>>(emptyList())

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = userDao.getUserLogged()
                livros.value = apiService.getUserLivros(user!!.id).body() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUser(name: String, biography: String, profileImageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUserLogged() // Acessando o usuário logado
            user?.let {
                val updatedUser = it.copy(
                    name = name,
                    biography = biography,
                    profileImageUrl = profileImageUrl
                )
                userDao.update(updatedUser) // Atualiza o usuário no banco
            }
        }
    }

    fun deleteBook(livroId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.deleteLivro(userDao.getUserLogged()!!.id, livroId)
                Log.d("TAG", "deleteBook: $response")
                livros.value = apiService.getUserLivros(user.value?.id ?: 0).body() ?: emptyList()
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.logout()
        }
    }
}