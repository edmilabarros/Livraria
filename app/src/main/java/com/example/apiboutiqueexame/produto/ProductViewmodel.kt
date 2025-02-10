import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.apiboutiqueexame.api.ApiService
import com.example.apiboutiqueexame.api.Livro
import com.example.apiboutiqueexame.api.LivroReq
import com.example.apiboutiqueexame.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}

class BookDetailViewModel(
    private val apiService: ApiService,
    private val userDao: UserDao
    ) : ViewModel() {
    private val _livros = MutableStateFlow<List<Livro>>(emptyList())
    val livros: StateFlow<List<Livro>> = _livros.asStateFlow()

    val userLivros = mutableStateOf<List<Livro>>(emptyList())

    // Inicializando corretamente um Livro vazio


    // Inicializando corretamente UiState como Idle
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchLivros()
    }

    fun fetchLivros() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getLivros()
                userLivros.value = apiService.getUserLivros(userDao.getUserLogged()?.id ?: 0).body() ?: emptyList()
                if (response.isSuccessful && response.body() != null) {
                    _livros.value = response.body()!!
                } else {
                    Log.e("BookDetail", "Erro na resposta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("BookDetail", "Exceção ao carregar livros", e)
            }
        }
    }


    fun publishBook() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            delay(2000)
            _uiState.value = UiState.Success
        }
    }



    fun searchBooks(query: String) {
        viewModelScope.launch {
            val filteredBooks = _livros.value.filter { livro ->
                livro.titulo.contains(query, ignoreCase = true) ||
                        livro.descricao.contains(query, ignoreCase = true) ||
                        livro.autor.nome.contains(query, ignoreCase = true)
            }
            _livros.value = filteredBooks
        }
    }

    fun showFilterOptions() {
        // Aqui você pode adicionar lógica para abrir um modal de filtros
        Log.d("BookDetailViewModel", "Abrindo opções de filtro")
    }






}
