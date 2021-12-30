package app.minhadespensa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.listagemCategorias.ListagemCategoriasScreen
import app.minhadespensa.listagemLocais.ListagemLocaisScreen
import app.minhadespensa.listagemLocais.ListagemProdutosLocalScreen
import app.minhadespensa.pesquisarProduto.PesquisarProdutoScreen
import app.minhadespensa.telacadastroCategoria.TelaCadastroCategoriaScreen
import app.minhadespensa.telacadastroProduto.TelaCadastroProdutoScreen
import app.minhadespensa.telacadastrolocal.TelaCadastroLocalScreen
import app.minhadespensa.ui.theme.MinhadepensaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhadepensaTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "TelaInicial" ){
                    composable("TelaInicial"){
                        IconTab(navHostController = navController)
                    }
                    composable("TelaCadastroLocal"){
                        val local = navController.previousBackStackEntry!!.arguments?.getParcelable<Local>("local")

                        ContentWrapper(){
                            TelaCadastroLocalScreen(navController = navController, local = local)
                        }

                    }
                    composable("TelaCadastroCategoria"){
                        ContentWrapper() {
                            TelaCadastroCategoriaScreen(navController = navController)
                        }
                    }
                    composable("TelaListagemLocal"){
                        ContentWrapper() {
                            ListagemLocaisScreen(navController = navController)
                        }
                    }
                    composable("TelaCadastroProduto"){
                        val produto = navController.previousBackStackEntry!!.arguments?.getParcelable<Produto>("produto")
                        ContentWrapper() {
                            TelaCadastroProdutoScreen(navController = navController, produto = produto)
                        }
                    }
                    composable("TelaListagemProdutos"){
                        ContentWrapper() {
                            ListagemLocaisScreen(navController = navController)
                        }
                    }
                    composable("TelaListagemCategorias"){
                        ContentWrapper() {
                            ListagemCategoriasScreen(navController = navController)
                        }
                    }
                    composable("TelaListagemProdutosLocal"){
                        val localId = navController.previousBackStackEntry!!.arguments!!.getInt("localId")
                        localId?.let{
                            ContentWrapper() {
                                ListagemProdutosLocalScreen(
                                    navController = navController,
                                    localId = it
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContentWrapper(modifier: Modifier = Modifier, content: @Composable() () -> Unit) {
    
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Minha Despensa") })
    },
    content = {
        Row {
            content()
        }
    })

}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun IconTab(navHostController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabData = listOf(
        "Locais" to Icons.Filled.Place,
        "Categorias" to Icons.Filled.List,
        "Produtos" to Icons.Filled.Add
    )

    Scaffold(
        content= {
            Column(modifier = Modifier.fillMaxSize()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabData.forEachIndexed { index, pair ->
                        Tab(selected = tabIndex == index, onClick = {
                            tabIndex = index
                        }, text = {
                            Text(text = pair.first)
                        }, icon = {
                            Icon(imageVector = pair.second, contentDescription = null)
                        })
                    }
                }
                when(tabIndex){
                    0 -> ListagemLocaisScreen(navController = navHostController)
                    1 -> ListagemCategoriasScreen(navController = navHostController)
                    2 -> PesquisarProdutoScreen(navController = navHostController)
                }
            }
        },
        topBar = {
            TopAppBar(title = { Text(text = "Minha Despensa") })
        }
    )
}