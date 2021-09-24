package app.minhadespensa.listagemLocais

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.repositories.LocaisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListagemLocaisViewModel @Inject constructor(private val dao: LocaisRepository) : ViewModel(){

    val locais : LiveData<List<Local>> = dao.findAll().asLiveData()

}