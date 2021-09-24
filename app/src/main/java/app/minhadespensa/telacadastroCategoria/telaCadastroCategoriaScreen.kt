package app.minhadespensa.telacadastroCategoria

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun TelaCadastroCategoriaScreen(viewModel: TelaCadastroLocalViewModel = hiltViewModel(), navController: NavController){

    val nome = viewModel.nomeCategoria.value

    val status = viewModel.status.observeAsState()

    if(status.value == true){
        navController.popBackStack()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){

        OutlinedTextField(label= {Text(text="Informe o nome da categoria")} , value = nome, onValueChange = {
            viewModel.onChangeNome(it)
        })

        Button(onClick = {viewModel.cadastrar()}) {
            Text(text = "Cadastrar")
        }

    }

}