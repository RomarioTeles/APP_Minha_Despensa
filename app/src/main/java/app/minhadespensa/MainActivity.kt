package app.minhadespensa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.minhadespensa.data.entities.Local
import app.minhadespensa.listagemCategorias.ListagemCategoriasScreen
import app.minhadespensa.listagemLocais.ListagemLocaisScreen
import app.minhadespensa.telacadastroCategoria.TelaCadastroCategoriaScreen
import app.minhadespensa.telacadastrolocal.TelaCadastroLocalScreen
import app.minhadespensa.ui.theme.MinhadepensaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                        TelaCadastroLocalScreen(navController = navController)
                    }
                    composable("TelaCadastroCategoria"){
                        TelaCadastroCategoriaScreen(navController = navController)
                    }
                    composable("TelaListagemLocal"){
                        ListagemLocaisScreen(navController = navController)
                    }
                    composable("TelaListagemCategoria"){
                        ListagemLocaisScreen(navController = navController)
                    }

                    /*composable("TelaDetalhesLocal"){
                        val cidade = navController.previousBackStackEntry!!.arguments!!.getParcelable<Local>("local")
                        cidade?.let{
                            TelaDetalhesScreen(navController = navController, cidade = it)
                        }
                    }*/
                }
            }
        }
    }
}

@Composable
fun TelaInicial(navHostController: NavHostController) {
    
    Column() {
        
    }

}

@Composable
fun IconTab(navHostController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabData = listOf(
        "Home" to Icons.Filled.Home,
        "Locais" to Icons.Filled.Place,
        "Categorias" to Icons.Filled.List
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
                    0 -> TelaInicial(navHostController = navHostController)
                    1 -> ListagemLocaisScreen(navController = navHostController)
                    2 -> ListagemCategoriasScreen(navController = navHostController)
                }
            }
        },
        topBar = {
            TopAppBar(title = { Text(text = "Minha Despensa") })
        }
    )
}