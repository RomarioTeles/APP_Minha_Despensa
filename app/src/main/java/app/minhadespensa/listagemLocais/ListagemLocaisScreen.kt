package app.minhadespensa.listagemLocais

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.minhadespensa.R
import app.minhadespensa.data.dto.LocalDTO
import app.minhadespensa.data.entities.Local

@Composable
fun ListagemLocaisScreen(viewModel: ListagemLocaisViewModel = hiltViewModel(), navController: NavController) {

    var isArquivados = viewModel.isArquivados.value

    var locais = viewModel.locais.observeAsState(listOf())

    var locaisArquivados = viewModel.locaisArquivados.observeAsState(listOf())

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("TelaCadastroLocal")
            },
            backgroundColor = Color.Red,
            content = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        )
    },
    content = {

        ConstraintLayout {
            val (refCardArquiv, refLista) = createRefs()

            Card(modifier = Modifier
                .defaultMinSize(minHeight = 70.dp)
                .fillMaxWidth()
                .constrainAs(refCardArquiv) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start)
                }
                .clickable {
                    viewModel.onChangeIsArquivados(!isArquivados)
                },
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painterResource(id = R.drawable.archive_arrow_down),
                        contentDescription = "Itens Arquivados",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp))

                    Text(
                        text = if (isArquivados) "Mostrar locais ativos" else "Locais arquivados",
                        color = colorResource(id = R.color.purple_700),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }


            LazyColumn(Modifier.constrainAs(refLista){
                top.linkTo(refCardArquiv.bottom, 16.dp)
                start.linkTo(parent.start)
            }){
                if(isArquivados){
                    itemsIndexed(locaisArquivados.value!!){
                        index, local -> meuCard(local = local, navController = navController)
                    }
                }else {
                    itemsIndexed(locais.value!!) { index, local ->
                        meuCard(local = local, navController = navController)
                    }
                }
            }
        }
    })

}

@Composable
fun meuCard(local: LocalDTO, navController: NavController){

    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(100.dp)
            .fillMaxWidth()
            .clickable {
                navController.currentBackStackEntry!!.arguments =
                    Bundle().apply {
                        putParcelable(
                            "local", Local(
                                localId = local.localId,
                                nome = local.nome!!,
                                deleteDate = local.deleteDate
                            )
                        )
                    }

                navController.navigate("TelaCadastroLocal")
            },
        elevation = 4.dp,
    ) {

        ConstraintLayout(

        ) {
            val ( nomeLocal, quantidade, image )= createRefs()

            Image(
                painterResource(id = R.drawable.file_cabinet), contentDescription = local.nome,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                    .height(48.dp)
                    .width(48.dp))

            Text(text = "${local.nome}",
                modifier = Modifier
                    .constrainAs(nomeLocal) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

            Text(text = "${local.quantidadeProdutos} itens",
                modifier = Modifier
                    .clickable {
                        navController.currentBackStackEntry!!.arguments =
                            Bundle().apply {
                                putInt("localId", local.localId!!)
                            }

                        navController.navigate("TelaListagemProdutosLocal")
                    }
                    .constrainAs(quantidade) {
                        top.linkTo(nomeLocal.bottom, 8.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                color = colorResource(id = R.color.purple_700)
            )

        }

    }
}