package app.minhadespensa.listagemProdutos

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.minhadespensa.R
import app.minhadespensa.data.entities.Local

@Composable
fun ListagemProdutosScreen(viewModel: ListagemProdutosViewModel = hiltViewModel(), navController: NavController) {

    val locais = viewModel.localprodutos.observeAsState(listOf())

    LazyColumn(){
        itemsIndexed(locais.value){
                index, local -> meuCard(local = local.local, navController = navController)
        }
    }

}

@Composable
fun meuCard(local: Local, navController: NavController){

    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(60.dp)
            .fillMaxWidth()
            .clickable {
                navController.currentBackStackEntry!!.arguments =
                    Bundle().apply {
                        putParcelable("local", local)
                    }

                navController.navigate("TelaDetalhesLocal")
            },
        elevation = 4.dp,
    ) {

        ConstraintLayout(

        ) {
            val ( text, image )= createRefs()

            Image(
                painterResource(id = R.drawable.file_cabinet), contentDescription = local.nome,
                modifier = Modifier
                    .constrainAs(image){
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                    .height(24.dp)
                    .width(24.dp))

            Text(text = local.nome,
                modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

        }

    }
}