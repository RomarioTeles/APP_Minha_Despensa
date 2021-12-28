package app.minhadespensa.telacadastroCategoria

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.repositories.CategoriasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TelaCadastroLocalViewModel @Inject constructor(private val dao: CategoriasRepository): ViewModel() {

    val categoriaId = mutableStateOf(0)

    val deleteDate : Date? = null

    val nomeCategoria = mutableStateOf("")

    val status : MutableLiveData<Boolean> = MutableLiveData()

    fun cadastrar(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val categoria = Categoria(nome = nomeCategoria.value)

                dao.insert(categoria)
                withContext(Dispatchers.Main){
                    status.value = true
                }
            }
        }
    }

    fun onChangeNome(newValue: String){
        nomeCategoria.value = newValue
    }

    fun remover() {
        TODO("Not yet implemented")
    }

    fun isVisible(): Boolean {
        return categoriaId.value != 0
    }

    fun getTextoBotaoArquivar(): String {
        return if (deleteDate == null) "Inativar" else "Ativar"
    }
}