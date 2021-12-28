package app.minhadespensa.listagemLocais

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import app.minhadespensa.data.dto.LocalDTO
import app.minhadespensa.data.dto.LocalWithProdutos
import app.minhadespensa.data.repositories.ProdutoLocalQuantidadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListagemLocaisViewModel @Inject constructor(private val dao: ProdutoLocalQuantidadeRepository) : ViewModel(){

    val localprodutos : LiveData<List<LocalDTO>> = dao.findAll().asLiveData()
}