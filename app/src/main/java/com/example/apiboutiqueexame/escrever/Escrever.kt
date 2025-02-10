package com.example.apiboutiqueexame.escrever

import BookDetailViewModel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.apiboutiqueexame.api.Autor
import com.example.apiboutiqueexame.api.LivroReq
import com.example.apiboutiqueexame.ui.theme.Purple600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscreverScreen(
    viewModel: EscreverViewModel,
) {
    var bookState by remember {
        mutableStateOf(
            LivroReq(
                titulo = "",
                descricao = "",
                editora = "",
                image = "",
                autor = Autor(nome = "", nacionalidade = ""),
                userId = 0
            )
        )
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // viewModel.updateBookCover(it.toString())
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Escreva seu Livro",
            style = MaterialTheme.typography.headlineMedium.copy(color = Purple600),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Author Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Informações do Autor",
                    style = MaterialTheme.typography.titleMedium.copy(color = Purple600),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = bookState.autor.nome,
                    onValueChange = { value ->
                        bookState = bookState.copy(autor = bookState.autor.copy(nome = value))
                    },
                    label = { Text("Nome do Autor", color = Purple600) },
                    placeholder = { Text("Seu nome completo", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )

                OutlinedTextField(
                    value = bookState.autor.nacionalidade ?: "",
                    onValueChange = { value ->
                        bookState =
                            bookState.copy(autor = bookState.autor.copy(nacionalidade = value))
                    },
                    label = { Text("Nacionalidade", color = Purple600) },
                    placeholder = { Text("Sua nacionalidade", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )
            }
        }


        // Book Details Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = bookState.titulo,
                    onValueChange = { bookState = bookState.copy(titulo = it) },
                    label = { Text("Título do Livro", color = Purple600) },
                    placeholder = { Text("Digite o título do seu livro", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Capa do Livro",
                    style = MaterialTheme.typography.titleSmall,
                    color = Purple600,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                        .clickable { imagePickerLauncher.launch("image/*") }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (!bookState.image.isNullOrEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(bookState.image),
                            contentDescription = "Capa do livro",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Upload capa",
                            modifier = Modifier.size(48.dp),
                            tint = Purple600
                        )
                    }
                }
            }
        }

        // Content Editor Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Conteúdo",
                    style = MaterialTheme.typography.titleSmall.copy(color = Purple600),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = bookState.descricao,
                    onValueChange = { bookState = bookState.copy(descricao = it) },
                    label = { Text("Conteúdo", color = Purple600) },
                    placeholder = {
                        Text(
                            "Comece a escrever seu livro aqui...",
                            color = Color.Black
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    textStyle = TextStyle(color = Color.Black),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    viewModel.postBook(bookState)
                    bookState = LivroReq(
                        titulo = "",
                        descricao = "",
                        editora = "",
                        image = "",
                        autor = Autor(nome = "", nacionalidade = ""),
                        userId = 0
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Purple600)
            ) {
                Text("Publicar", color = Color.White)
            }
        }
    }
}
