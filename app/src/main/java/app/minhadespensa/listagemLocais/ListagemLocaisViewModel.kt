package app.minhadespensa.listagemLocais

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import app.minhadespensa.data.dto.LocalDTO
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.repositories.ProdutoLocalQuantidadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListagemLocaisViewModel @Inject constructor(private val dao: ProdutoLocalQuantidadeRepository) : ViewModel(){

    val locais : LiveData<List<LocalDTO>> = dao.findAll().asLiveData()

    val locaisArquivados : LiveData<List<LocalDTO>> = dao.findAllInativos().asLiveData()

    val isArquivados = mutableStateOf(false)

    fun onChangeIsArquivados(value: Boolean){
        isArquivados.value = value
    }

}