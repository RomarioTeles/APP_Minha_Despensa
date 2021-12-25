package app.minhadespensa.listagemLocais

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
import app.minhadespensa.data.dto.ProdutosWithLocais

@Composable
fun ListagemProdutosLocalScreen(localId: Int, viewModel: ListagemProdutosLocalViewModel = hiltViewModel(), navController: NavController) {

    viewModel.getProdutos(localId)

    val produtos = viewModel.localprodutos.observeAsState(listOf())

    LazyColumn(){
        itemsIndexed(produtos.value){
                index, produto -> meuCard(produto = produto, navController = navController)
        }
    }

}

@Composable
fun meuCard(produto: ProdutosWithLocais, navController: NavController){

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
            val ( nomeLocal, quantidade, status, image )= createRefs()

            Image(
                painterResource(id = R.drawable.file_cabinet), contentDescription = produto.produto.nome,
                modifier = Modifier
                    .constrainAs(image){
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                    .height(48.dp)
                    .width(48.dp))

            Text(text = produto.produto.nome,
                modifier = Modifier
                    .constrainAs(nomeLocal) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

            Text(text = "${produto.quantidade} itens ",
                modifier = Modifier
                    .constrainAs(quantidade) {
                        top.linkTo(nomeLocal.bottom, 8.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

            Text(text = "${produto.status.capitalizedName()}",
                modifier = Modifier
                    .constrainAs(status) {
                        top.linkTo(quantidade.bottom, 8.dp)
                        start.linkTo(image.end, 16.dp)
                    },
                textAlign = TextAlign.Center
            )

        }

    }
}