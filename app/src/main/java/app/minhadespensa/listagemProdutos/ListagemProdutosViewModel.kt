package app.minhadespensa.listagemProdutos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.repositories.ProdutoLocalQuantidadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListagemProdutosViewModel @Inject constructor(private val dao: ProdutoLocalQuantidadeRepository) : ViewModel(){

    val localprodutos : LiveData<List<LocalWithProdutos>> = dao.findAll().asLiveData()

}