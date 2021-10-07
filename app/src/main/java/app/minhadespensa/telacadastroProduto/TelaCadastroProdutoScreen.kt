package app.minhadespensa.telacadastroProduto

import android.view.Gravity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import app.minhadespensa.R
import app.minhadespensa.data.entities.EnumStatus
import app.minhadespensa.data.entities.Local
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TelaCadastroProdutoScreen(
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    Content(
        navController = navController,
        coroutineScope = coroutineScope,
        scaffoldState = bottomSheetScaffoldState
    )
}

@ExperimentalMaterialApi
@Composable
fun MainContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    viewModel: TelaCadastroProdutoViewModel,
    navController: NavController
) {

    val nome = viewModel.nomeProduto.value

    val codigo = viewModel.codigoProduto.value

    val categoriaId = viewModel.categoriaId.value

    val categorias = viewModel.categorias

    val isCadastrado = viewModel.isCadastrado.observeAsState()

    val categoriaExpanded = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    if (isCadastrado.value == true) {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {

        Text(text = "Informações do Produto", fontSize = 20.sp)

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Informe o código do produto") },
            value = codigo,
            onValueChange = {
                viewModel.onChangeCodigo(it)
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Informe o nome do produto") },
            value = nome,
            onValueChange = {
                viewModel.onChangeNome(it)
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        MyDropDownMenu(
            label = "Categoria",
            list = categorias.map { it.nome },
            requestToOpen = categoriaExpanded.value,
            request = { value -> categoriaExpanded.value = value },
            selectedString = viewModel.getNomeCategoriaById(categoriaId) ?: "",
            onSelect = { viewModel.setCategoriaByNome(it) }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        LocaisPills(list = viewModel.getLocaisSelecionados(), onClick = {
            scope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        })

        Spacer(modifier = Modifier.padding(16.dp))


        Button(
            onClick = { viewModel.cadastrar() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)

        ) {
            Text(text = "Cadastrar")
        }

    }
}

@ExperimentalMaterialApi
@Composable
fun Content(
    scaffoldState : BottomSheetScaffoldState,
    coroutineScope : CoroutineScope,
    viewModel: TelaCadastroProdutoViewModel = hiltViewModel(),
    navController: NavController
) {

    val quantidade = viewModel.quantidade.value

    val statusProduto = viewModel.statusProduto.value

    val localId = viewModel.localId.value

    val locais = viewModel.locais

    val localExpanded = remember { mutableStateOf(false) }

    val statusExpanded = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxSize()
                    .height(200.dp)
            ) {
                Column(
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(width = 1.dp, color = Color.LightGray)
                        .padding(8.dp)
                ) {


                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(text = "Informe a quantidade do produto") },
                        value = if (quantidade == 0) "" else quantidade.toString(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            viewModel.onChangeQuantidade(it)
                        }
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    MyDropDownMenu(
                        label = "Local",
                        list = locais.map { it.nome },
                        requestToOpen = localExpanded.value,
                        request = { localExpanded.value = it },
                        selectedString = viewModel.getNomeLocalById(localId) ?: "",
                        onSelect = { viewModel.setLocalByNome(it) }
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    MyDropDownMenu(
                        label = "Status",
                        list = viewModel.getListaStatus().map { it.name },
                        requestToOpen = statusExpanded.value,
                        request = { statusExpanded.value = it },
                        selectedString = statusProduto.capitalizedName(),
                        onSelect = { viewModel.onChangeStatusProduto(EnumStatus.valueOf(it)) }
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Button(
                        onClick = {
                            viewModel.addlocal()
                            coroutineScope.launch {
                                scaffoldState.bottomSheetState.collapse()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Adicionar Local")
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                }

            }
        }, sheetPeekHeight = 0.dp
    ) {
        MainContent(scaffoldState, coroutineScope, viewModel, navController)
    }
}

@Composable
fun LocaisPills(list: List<Local>, onClick: () -> Unit) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = "Localização", fontSize = 20.sp)
            IconButton(onClick) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Atribua um local")
            }
        }

        LazyRow {
            itemsIndexed(list) { index, it ->
                Box(modifier = Modifier
                    .padding(8.dp)
                    .background(Color.LightGray)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(
                        onClick = { }
                    )) {
                    Text(
                        text = it.nome,
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun MyDropDownMenu(
    label: String,
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: String,
    onSelect: (String) -> Unit
) {

    Box {
        Column {
            OutlinedTextField(
                value = selectedString,
                onValueChange = onSelect,
                label = { Text(text = label) },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownList(
                requestToOpen = requestToOpen,
                list = list,
                request = request,
                onSelect = onSelect
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = { request(true) }
                )
        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    onSelect: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    onSelect(it)
                }
            ) {
                Text(it, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}


