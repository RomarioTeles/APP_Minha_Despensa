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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.minhadespensa.R
import app.minhadespensa.data.dto.LocalDTO
import app.minhadespensa.data.dto.LocalWithProdutos

@Composable
fun ListagemLocaisScreen(viewModel: ListagemLocaisViewModel = hiltViewModel(), navController: NavController) {

    val locaisp = viewModel.localprodutos.observeAsState(listOf())

    var locais = locaisp.value?.distinctBy { it.localId }

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
        LazyColumn(){
            itemsIndexed(locais){
                    index, local -> meuCard(local = local, navController = navController)
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
                        putInt("localId", local.localId!!)
                    }

                navController.navigate("TelaListagemProdutosLocal")
            },
        elevation = 4.dp,
    ) {

        ConstraintLayout(

        ) {
            val ( nomeLocal, quantidade, image )= createRefs()

            Image(
                painterResource(id = R.drawable.file_cabinet), contentDescription = local.nome,
                modifier = Modifier
                    .constrainAs(image){
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
                    .constrainAs(quantidade) {
                        top.linkTo(nomeLocal.bottom, 8.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

        }

    }
}