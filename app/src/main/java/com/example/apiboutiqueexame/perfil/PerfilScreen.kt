package com.example.apiboutiqueexame.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.apiboutiqueexame.api.Livro
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    navController: NavController
) {
    val user by viewModel.user.collectAsState()

    // Obtendo a lista de livros da ViewModel de Livros
    val livros = viewModel.livros.value

    var name by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            biography = it.biography
            profileImage = it.profileImageUrl ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto de Perfil
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.LightGray) ,// Passa o contexto corretamente aqui
            contentAlignment = Alignment.Center
        ) {
            if (profileImage.isNotEmpty()) {
                AsyncImage(
                    model = profileImage,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(Icons.Default.Person, contentDescription = "Foto de perfil", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campos Nome e Biografia
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = biography,
            onValueChange = { biography = it },
            label = { Text("Biografia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Salvar
        Button(
            onClick = {
                viewModel.updateUser(name, biography, profileImage)
                navController.popBackStack() // Volta para a tela anterior
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }

        // Livros Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Meus Livros",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Gerencie sua coleção de livros",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Exibindo a lista de livros com LazyColumn
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(livros) { livro ->
                        BookItem(
                            livro = livro,
                            onDelete = {
                                viewModel.deleteBook(livro.id)
                            }
                        )
                    }
                }
                Spacer(Modifier.height(50.dp))
                Button(
                    onClick = {
                        viewModel.logout()
                        navController.navigate("login")// Volta para a tela in
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sair")
                }
            }
        }
    }
}


@Composable
fun BookItem(
    livro: Livro,
    onClick: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = livro.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = livro.autor.nome,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF0066CC)
                    )
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = livro.editora ?: "Sem Editora",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Ícone para editar livro
                IconButton(onClick = { onClick?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF666666)
                    )
                }
                // Ícone para deletar livro
                IconButton(onClick = { onDelete?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar",
                        tint = Color(0xFFDC3545)
                    )
                }
            }
        }
    }
}
