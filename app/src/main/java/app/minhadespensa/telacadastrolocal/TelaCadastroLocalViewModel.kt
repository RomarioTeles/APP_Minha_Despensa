package app.minhadespensa.telacadastrolocal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.repositories.LocaisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TelaCadastroLocalViewModel @Inject constructor(private val dao: LocaisRepository): ViewModel() {

    val nomeLocal = mutableStateOf("")

    val localId = mutableStateOf(0)

    val status : MutableLiveData<Boolean> = MutableLiveData()

    val deleteDate : Date? = null

    fun cadastrar(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val local = Local(nome = nomeLocal.value)

                dao.insert(local)
                withContext(Dispatchers.Main){
                    status.value = true
                }
            }
        }
    }

    fun onChangeNome(newValue: String){
        nomeLocal.value = newValue
    }

    fun remover() {
        TODO("Not yet implemented")
    }

    fun isVisible(): Boolean {
        return localId.value != 0
    }

    fun getTextoBotaoArquivar(): String {
        return if (deleteDate == null) "Inativar" else "Ativar"
    }
}