package app.minhadespensa.telacadastrolocal

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun TelaCadastroLocalScreen(viewModel: TelaCadastroLocalViewModel = hiltViewModel(), navController: NavController){

    val nome = viewModel.nomeLocal.value

    val status = viewModel.status.observeAsState()

    if(status.value == true){
        navController.popBackStack()
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){

        val (outlinedTextField, button) = createRefs()

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(outlinedTextField) {
                bottom.linkTo(button.top, 16.dp)
            },
            label= {Text(text="Informe o nome do local")} ,
            value = nome,
            onValueChange = {
                viewModel.onChangeNome(it)
            }
        )

        Button(
            onClick = {viewModel.cadastrar()},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(text = "Cadastrar")
        }

    }

}