package app.minhadespensa.listagemCategorias

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.repositories.CategoriasRepository
import app.minhadespensa.data.repositories.LocaisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListagemCategoriasViewModel @Inject constructor(private val dao: CategoriasRepository) : ViewModel(){

    val categorias : LiveData<List<Categoria>> = dao.findAll().asLiveData()

}