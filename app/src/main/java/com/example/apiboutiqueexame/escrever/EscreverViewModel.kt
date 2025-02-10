package com.example.apiboutiqueexame.escrever;

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiboutiqueexame.api.ApiService
import com.example.apiboutiqueexame.api.LivroReq
import com.example.apiboutiqueexame.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EscreverViewModel(
    private val apiService: ApiService,
    private val userDao: UserDao
) : ViewModel() {

    fun postBook(livroReq: LivroReq) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val post = livroReq.copy(userId = userDao.getUserLogged()!!.id)
                val response =
                    apiService.addLivro(post) // Certifique-se de que a API aceita esse formato
                Log.d("TAG", "postBook: {$response}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
