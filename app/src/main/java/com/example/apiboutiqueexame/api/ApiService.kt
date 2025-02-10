
package com.example.apiboutiqueexame.api

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("livros")
    suspend fun getLivros(): Response<List<Livro>>

    @GET("livros/user/{userid}")
    suspend fun getUserLivros(@Path("userid") userId: Int): Response<List<Livro>>

    @POST("livros")
    suspend fun  addLivro(@Body livro: LivroReq): Response<Livro>

    @PUT("livros/{id}")
    suspend fun updateLivro(@Path("id") id: String, @Body livro: Livro): Livro

    @DELETE("livros/{id}/{livroid}")
    suspend fun deleteLivro(@Path("id") id: Int, @Path("livroid") livroid: String): Response<Unit>
}
