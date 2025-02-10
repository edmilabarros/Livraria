package com.example.apiboutiqueexame.api

import com.google.gson.annotations.SerializedName

data class Livro(
    @SerializedName("_id") val id: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("editora") val editora: String?,
    @SerializedName("image") val imageUrl: String?,
    @SerializedName("autor") val autor: Autor,
    val userId: Int
)

data class LivroReq(
    val titulo: String,
    val descricao: String,
    val editora: String?,
    val image: String,
    val userId: Int,
    val autor: Autor

)

data class Autor(
    val nome: String,
    val nacionalidade: String
)
