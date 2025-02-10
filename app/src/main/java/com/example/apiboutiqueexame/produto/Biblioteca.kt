package com.example.apiboutiqueexame.produto

import BookDetailViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.apiboutiqueexame.R
import com.example.apiboutiqueexame.api.Livro
import com.example.apiboutiqueexame.ui.theme.Purple600

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BibliotecaScreen(viewModel: BookDetailViewModel, navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }


    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNa(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            Text(
                text = "Biblioteca",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Purple600,
            )

            SearchBar(
                onSearch = { viewModel.searchBooks(it) },
                onFilter = { viewModel.showFilterOptions() }
            )

            BookList(
                livros = viewModel.livros.value,
                onBookClick = {  }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    onFilter: () -> Unit
) {
    OutlinedTextField(
        value = "",
        onValueChange = { onSearch(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Buscar na biblioteca") },
        trailingIcon = {
            IconButton(onClick = onFilter) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.FilterList,
                    contentDescription = "Filtrar"
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
fun BookList(
    livros: List<Livro>,
    onBookClick: (Livro) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(livros) { livro ->
            BookItem(livro = livro, onClick = { onBookClick(livro) })
            Divider()
        }
    }
}
@Composable
fun BookItem(
    livro: Livro,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagem do livro
        AsyncImage(
            model = "http://10.0.2.2:3000/img/${livro.imageUrl}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp) // 🔥 Ajustei o tamanho para ficar proporcional
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )

        // Informações do livro
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = livro.titulo,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = livro.autor.nome,
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = "Último acesso: 2d atrás",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.book),
            contentDescription = "Descrição do Ícone",
            modifier = Modifier.size(24.dp)
        )


    }
}

@Composable
fun SortButton(
    currentSort: String,
    onSortChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ordenar por: $currentSort")
            IconButton(onClick = { onSortChange("Recentes") }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                    contentDescription = "Mudar ordenação"
                )
            }
        }
    }
}

@Composable
fun BottomNa(navController: NavController) {
    val items = listOf(
        NavigationItem("Início", Icons.Default.Home, "home"),
        NavigationItem("Biblioteca", Icons.Default.Book, "book"),
        NavigationItem("Escrever", Icons.Default.Edit, "write"),
        NavigationItem("Perfil", Icons.Default.Person,  "perfil")
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(20.dp)
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




