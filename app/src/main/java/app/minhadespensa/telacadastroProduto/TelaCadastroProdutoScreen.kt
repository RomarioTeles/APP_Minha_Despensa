package app.minhadespensa.telacadastroProduto

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.minhadespensa.data.entities.EnumStatus
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TelaCadastroProdutoScreen(
    produto: Produto?=null,
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    Content(
        produto = produto,
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

    val produtoLocais = viewModel.produtoLocais.value

    val hasDeleteDate = viewModel.hasDeleteDate.value

    val categoriaExpanded = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    if (isCadastrado.value == true) {
        navController.navigate("TelaListagemProdutos")
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {

        val (refTextInfo, refInputCodigo, refInputNome, refBoxCat, refSave, refDelete ) = createRefs()

        Text(text = "Informações do Produto", fontSize = 20.sp,
            modifier = Modifier.constrainAs(refTextInfo){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            })

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(refInputCodigo) {
                    start.linkTo(parent.start)
                    top.linkTo(refTextInfo.bottom, 8.dp)
                },
            label = { Text(text = "Informe o código do produto") },
            value = codigo,
            onValueChange = {
                viewModel.onChangeCodigo(it)
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(refInputNome) {
                    start.linkTo(parent.start)
                    top.linkTo(refInputCodigo.bottom, 8.dp)
                },
            label = { Text(text = "Informe o nome do produto") },
            value = nome,
            onValueChange = {
                viewModel.onChangeNome(it)
            }
        )

        Column(modifier = Modifier.constrainAs(refBoxCat){
            start.linkTo(parent.start)
            top.linkTo(refInputNome.bottom, 8.dp)
        }) {

            MyDropDownMenu(
                label = "Categoria",
                list = categorias.map { it.nome },
                requestToOpen = categoriaExpanded.value,
                request = { value -> categoriaExpanded.value = value },
                selectedString = viewModel.getNomeCategoriaById(categoriaId) ?: "",
                onSelect = { viewModel.setCategoriaByNome(it) }
            )

            LocaisPills(list = produtoLocais, onAddClick = {
                scope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }, onItemSelected = {
                viewModel.onChangeLocal(it.localId)
                viewModel.onChangeQuantidade(it.quantidade.toString())
                viewModel.onChangeStatusProduto(it.status)
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            })
        }

        if(viewModel.isVisible()) {
            Button(
                onClick = { viewModel.remover() },
                modifier = Modifier.fillMaxWidth(0.5f).padding(8.dp)
                    .height(50.dp)
                    .constrainAs(refDelete) {
                        top.linkTo(refBoxCat.bottom, 16.dp)
                    }
            ) {
                Text(text = if(hasDeleteDate) "Ativar" else "Inativar")
            }
        }

        Button(
            onClick = {viewModel.cadastrar()},
            modifier = Modifier.fillMaxWidth(0.5f).padding(8.dp)
                .height(50.dp)
                .constrainAs(refSave) {
                    top.linkTo(refBoxCat.bottom, 16.dp)
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

@ExperimentalMaterialApi
@Composable
fun Content(
    produto: Produto?,
    scaffoldState : BottomSheetScaffoldState,
    coroutineScope : CoroutineScope,
    viewModel: TelaCadastroProdutoViewModel = hiltViewModel(),
    navController: NavController
) {

    viewModel.set(produto)

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

                    Row {
                        Button(
                            onClick = {
                                viewModel.removeLocal()
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.collapse()
                                }
                            },
                            modifier = Modifier.weight(1.0f)
                        ) {
                            Text(text = "Remover Local")
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Button(
                            onClick = {
                                viewModel.addlocal()
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.collapse()
                                }
                            },
                            modifier = Modifier.weight(1.0f)
                        ) {
                            Text(text = "Adicionar Local")
                        }
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
fun LocaisPills(list: List<ProdutoLocalQuantidade>, onAddClick: () -> Unit, onItemSelected: (ProdutoLocalQuantidade) -> Unit) {

    Column(horizontalAlignment = Alignment.Start) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text( modifier = Modifier.weight(2.0f),
                text = "Localização", fontSize = 20.sp)
            IconButton(onAddClick,
                modifier = Modifier
                    .padding(4.dp)
                    .clipToBounds()
                    .border(
                        1.dp,
                        colorResource(id = android.R.color.darker_gray),
                        RoundedCornerShape(15.dp)
                    )
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Atribua um local")
            }
        }

        LazyRow(
            Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .background(Color.Transparent)
                .clipToBounds()
                .border(
                    2.dp,
                    colorResource(id = android.R.color.darker_gray),
                    RoundedCornerShape(20.dp)
                )
            ) {
            itemsIndexed(list) { index, it ->
                Box(modifier = Modifier
                    .padding(4.dp)
                    .background(Color.Transparent)
                    .clipToBounds()
                    .border(
                        2.dp,
                        colorResource(id = android.R.color.darker_gray),
                        RoundedCornerShape(5.dp)
                    )
                    .clickable(
                        onClick = { onItemSelected(it) }
                    )) {
                    ConstraintLayout(modifier = Modifier.padding(8.dp)){
                        val (refNome, refQuant, refStatus) = createRefs()
                        Text(
                            modifier = Modifier.constrainAs(refNome){
                              start.linkTo(parent.start)
                              top.linkTo(parent.top)
                            },
                            text = "${it.localNome}",
                            color = colorResource(id = android.R.color.darker_gray),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.constrainAs(refQuant){
                                start.linkTo(parent.start)
                                top.linkTo(refNome.bottom)
                            },
                            text = "${it.quantidade}",
                            color = colorResource(id = android.R.color.darker_gray),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.constrainAs(refStatus){
                                start.linkTo(parent.start)
                                top.linkTo(refQuant.bottom)
                            },
                            text = "${it.status}",
                            color = colorResource(id = android.R.color.darker_gray),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }

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


