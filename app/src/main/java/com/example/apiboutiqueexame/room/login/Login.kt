package com.example.apiboutiqueexame.room.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: UserViewModel, navController: NavController) {
    var email by remember { mutableStateOf("admin@email.com") }
    var password by remember { mutableStateOf("123456") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F5))
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xFFE91E63) // Cor rosa do logo
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = "Boutique Elegance",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )

                // Subtítulo
                Text(
                    text = "Entre em sua conta",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de e-mail
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = {
                        viewModel.getUser(email, password)
                        /*navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }*/
                        //Toast.makeText(context, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()

                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        "Entrar",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Link para cadastro
                TextButton(onClick = { viewModel.registerUser(email, password) }) {
                    Text(
                        "Não tem uma conta? Cadastre-se",
                        color = Color(0xFFE91E63),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}