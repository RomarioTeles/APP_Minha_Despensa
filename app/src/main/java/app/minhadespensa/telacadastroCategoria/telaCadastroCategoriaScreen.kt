package app.minhadespensa.telacadastroCategoria

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@ExperimentalComposeUiApi
@Composable
fun TelaCadastroCategoriaScreen(viewModel: TelaCadastroLocalViewModel = hiltViewModel(), navController: NavController){

    val nome = viewModel.nomeCategoria.value

    val status = viewModel.status.observeAsState()

    if(status.value == true){
        navController.navigate("TelaListagemCategorias")
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){

        val (outlinedTextField, refSave, refDelete) = createRefs()

        val keyboardController = LocalSoftwareKeyboardController.current

        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(outlinedTextField) {
                    top.linkTo(parent.top, 16.dp)
                },
            label= {Text(text="Informe o nome da categoria")} ,
            value = nome,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            onValueChange = {
                viewModel.onChangeNome(it)
            }
        )

        if(viewModel.isVisible()) {
            Button(
                onClick = { viewModel.remover() },
                modifier = Modifier.fillMaxWidth(0.5f).padding(8.dp)
                    .height(50.dp)
                    .constrainAs(refDelete) {
                        top.linkTo(outlinedTextField.bottom, 16.dp)
                    }
            ) {
                Text(text = viewModel.getTextoBotaoArquivar())
            }
        }

        Button(
            onClick = {viewModel.cadastrar()},
            modifier = Modifier.fillMaxWidth(0.5f).padding(8.dp)
                .height(50.dp)
                .constrainAs(refSave) {
                    top.linkTo(outlinedTextField.bottom, 16.dp)
                    start.linkTo(refDelete.end)
                }
        ) {
            Text(text = "Gravar")
        }

        createHorizontalChain(
            refDelete, refSave, chainStyle = ChainStyle.Packed
        )

    }

}