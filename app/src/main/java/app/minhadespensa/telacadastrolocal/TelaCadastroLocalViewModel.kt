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
import javax.inject.Inject

@HiltViewModel
class TelaCadastroLocalViewModel @Inject constructor(private val dao: LocaisRepository): ViewModel() {

    val nomeLocal = mutableStateOf("")

    val status : MutableLiveData<Boolean> = MutableLiveData()

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
}