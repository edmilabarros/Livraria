package com.example.apiboutiqueexame.produto

import BookDetailViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.apiboutiqueexame.api.Livro
import com.example.apiboutiqueexame.api.provideRetrofit
import com.example.apiboutiqueexame.ui.theme.Purple600


@Composable
fun BookWorldApp(viewModel: BookDetailViewModel, navController: NavController) {
    val apiService = provideRetrofit()


    val livros by viewModel.livros.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(searchQuery, onSearchQueryChange = { searchQuery = it })

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f) // Permite que a lista ocupe o espaço disponível sem quebrar o layout
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Bottom // 🔥 Isso empurra os itens para baixo
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { WeeklyHighlight(livros.firstOrNull()) }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item { ContinueReading(livros) }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item { Recommendations(livros) }
            }
        }
        BottomNav(navController)
    }
}


@Composable
fun Header(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "BookLover",
            style = MaterialTheme.typography.titleLarge,
            color = Purple600,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .height(30.dp)
                .weight(1f),
            placeholder = { Text("Buscar...", fontSize = 8.sp) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    modifier = Modifier.size(18.dp)
                )
            },
            shape = RoundedCornerShape(20.dp), // Bordas arredondadas
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White, // Mantém o fundo branco
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Purple600, // Muda a cor da borda ao focar
                unfocusedIndicatorColor = Color.Gray // Cor padrão da borda
            )
        )
    }
}


@Composable
fun WeeklyHighlight(book: Livro?) {
    book?.let {
        Column {
            Text(
                text = "Destaque da Semana",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Purple600,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF9439E8), Color(0xFFEA1388))
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = "http://10.0.2.2:3000/img/${book.imageUrl}",
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = book.titulo,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = book.autor.nome ?: "sem nome",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = book.descricao ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFFFFF)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "Ler agora",
                                color = Color(0xFFAD80FF)
                            )

                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ContinueReading(livros: List<Livro>) {
    Column {
        Text(
            "Continuar Lendo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Purple600,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(livros.take(3)) { book ->
                Column(
                    modifier = Modifier.width(120.dp)
                ) {
                    AsyncImage(
                        model = "http://10.0.2.2:3000/img/${book.imageUrl}",
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .height(160.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Text(
                        text = book.titulo,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = book.autor.nome,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun Recommendations(livros: List<Livro>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Recomendados",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Purple600,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            livros.chunked(2).forEach { rowBooks ->  // Agrupa os livros em pares
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowBooks.forEach { book ->
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = "http://10.0.2.2:3000/img/${book.imageUrl}",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray)
                            )

                            // Book title
                            Text(
                                text = book.titulo,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                maxLines = 2,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            // Author name
                            Text(
                                text = book.autor.nome,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        NavigationItem("Início", Icons.Default.Home, "home"),
        NavigationItem("Biblioteca", Icons.Default.Book, "book"),
        NavigationItem("Escrever", Icons.Default.Edit, "write"),
        NavigationItem("Perfil", Icons.Default.Person, "perfil")
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Column( // 🔥 Organiza ícone e texto em coluna
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp) // 🔥 Diminui espaçamento
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(20.dp) // 🔥 Ícone menor
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                selected = navController.currentDestination?.route == item.route,
                onClick = { navController.navigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6750A4),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF6750A4),
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.White
                )
            )
        }
    }
}

