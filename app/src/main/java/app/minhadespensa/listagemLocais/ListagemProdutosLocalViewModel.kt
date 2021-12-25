package app.minhadespensa.listagemLocais

import androidx.lifecycle.*
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.dto.ProdutosWithLocais
import app.minhadespensa.data.repositories.ProdutoLocalQuantidadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListagemProdutosLocalViewModel @Inject constructor(private val dao: ProdutoLocalQuantidadeRepository) : ViewModel(){

    val localprodutos : MutableLiveData<List<ProdutosWithLocais>> by lazy {
        MutableLiveData<List<ProdutosWithLocais>>()
    }

    fun getProdutos(localId: Int){
        viewModelScope.launch {
            dao.findByLocalId(localId).collect {
                localprodutos.value = it
            }
        }
    }
}