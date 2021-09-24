package app.minhadespensa.listagemCategorias

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Local

@Composable
fun ListagemCategoriasScreen(viewModel: ListagemCategoriasViewModel = hiltViewModel(), navController: NavController) {

    val categorias = viewModel.categorias.observeAsState(listOf())

    LazyColumn(){
        itemsIndexed(categorias.value){
                index, categoria -> meuCard(categoria = categoria, navController = navController)
        }
    }

}

@Composable
fun meuCard(categoria: Categoria, navController: NavController){

    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(60.dp)
            .fillMaxWidth()
            .clickable {
                navController.currentBackStackEntry!!.arguments =
                    Bundle().apply {
                        putParcelable("categoria", categoria)
                    }

                navController.navigate("TelaDetalhesCategoria")
            },
        elevation = 4.dp,
    ) {

        ConstraintLayout(

        ) {
            val text = createRef()

            Text(text = categoria.nome,
                modifier = Modifier.fillMaxWidth().constrainAs(text){
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
                textAlign = TextAlign.Center
            )

        }

    }
}