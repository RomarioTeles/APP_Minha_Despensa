package app.minhadespensa.pesquisarProduto

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.minhadespensa.data.dto.ProdutoQuantidade
import app.minhadespensa.data.dto.ProdutosWithLocais
import app.minhadespensa.data.repositories.ProdutoLocalQuantidadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PesquisarProdutoViewModel @Inject constructor(val repository: ProdutoLocalQuantidadeRepository): ViewModel(){

    val searchkey = mutableStateOf("")

    val produtosComLocais : MutableLiveData<List<ProdutoQuantidade>> by lazy {
        MutableLiveData<List<ProdutoQuantidade>>()
    }

    fun pesquisar(){
        viewModelScope.launch {
            repository.pesquisarProduto(searchkey.value).collect {
                if(it.isNotEmpty()) {
                    produtosComLocais.value = it.distinctBy { itt -> itt.produto }
                }
            }
        }
    }

    fun onChangeSearchKey(searchKeyParam: String) {
        searchkey.value = searchKeyParam
        pesquisar()
    }

}