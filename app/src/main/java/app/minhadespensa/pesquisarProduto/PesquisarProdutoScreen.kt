package app.minhadespensa.pesquisarProduto

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.minhadespensa.R
import app.minhadespensa.data.dto.ProdutoQuantidade
import app.minhadespensa.data.dto.ProdutosWithLocais

@ExperimentalComposeUiApi
@Composable
fun PesquisarProdutoScreen(viewModel: PesquisarProdutoViewModel = hiltViewModel(), navController: NavController) {

    val searchkey = viewModel.searchkey.value

    val produtos = viewModel.produtosComLocais.observeAsState(listOf())

    viewModel.pesquisar()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("TelaCadastroProduto")
            },
            backgroundColor = Color.Red,
            content = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        )
    },
        content = {
            ConstraintLayout(Modifier.padding(16.dp)) {

                val (outlinedTextField, listaprodutos) = createRefs()

                val keyboardController = LocalSoftwareKeyboardController.current

                val focusManager = LocalFocusManager.current

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(outlinedTextField) {
                            top.linkTo(parent.top)
                        },
                    label= { Text(text="Informe o nome ou código do produto") } ,
                    value = searchkey,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }),
                    onValueChange = {
                        viewModel.onChangeSearchKey(it)
                    }
                )

                LazyColumn(Modifier.constrainAs(listaprodutos){
                    top.linkTo(outlinedTextField.bottom, 16.dp)
                }){
                    itemsIndexed(produtos.value){
                            index, produto -> meuCard(produto = produto, navController = navController)
                    }
                }

            }
        })
}

@Composable
fun meuCard(produto: ProdutoQuantidade, navController: NavController){

    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(120.dp)
            .fillMaxWidth()
            .clickable {
                navController.currentBackStackEntry!!.arguments =
                    Bundle().apply {
                        putParcelable("produto", produto.produto)
                    }

                navController.navigate("TelaCadastroProduto")
            },
        elevation = 4.dp,
    ) {

        ConstraintLayout(

        ) {
            val ( nomeLocal, locais, quantidade, image )= createRefs()

            Image(
                painterResource(id = R.drawable.file_cabinet), contentDescription = produto.produto.nome,
                modifier = Modifier
                    .constrainAs(image){
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                    .height(48.dp)
                    .width(48.dp))

            Text(text = "${produto.produto.nome}",
                modifier = Modifier
                    .constrainAs(nomeLocal) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

            Text(text = produto.locais.joinToString(separator = ", "){"${it.nome}"} ,
                modifier = Modifier
                    .constrainAs(locais) {
                        top.linkTo(nomeLocal.bottom, 8.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

            Text(text = "${produto.quantidade} itens" ,
                modifier = Modifier
                    .constrainAs(quantidade) {
                        top.linkTo(locais.bottom, 8.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )
        }

    }
}