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
class TelaCadastroLocalViewModel @Inject constructor(private val repository: LocaisRepository): ViewModel() {

    val nomeLocal = mutableStateOf("")

    val localId = mutableStateOf(0)

    val status : MutableLiveData<Boolean> = MutableLiveData()

    var deleteDate : Date? = null

    val hasDeleteDate = mutableStateOf(false)

    fun cadastrar(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val local = Local(localId = localId.value,nome = nomeLocal.value)

                if(local.localId == 0) {
                    repository.insert(local)
                }else{
                    repository.update(local)
                }

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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.ativarOrDesativar(localId.value)
            }
        }
        deleteDate = if(hasDeleteDate.value) null else Date()
        hasDeleteDate.value = deleteDate != null
    }

    fun isVisible(): Boolean {
        return localId.value != 0
    }

    fun set(local: Local?) {
        if(local != null && localId.value == 0){
            nomeLocal.value = local.nome
            localId.value = local.localId!!
            deleteDate = local.deleteDate
            hasDeleteDate.value = deleteDate != null
        }
    }
}