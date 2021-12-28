package app.minhadespensa.telacadastroProduto

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import app.minhadespensa.data.entities.*
import app.minhadespensa.data.repositories.CategoriasRepository
import app.minhadespensa.data.repositories.LocaisRepository
import app.minhadespensa.data.repositories.ProdutosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TelaCadastroProdutoViewModel @Inject constructor(val produtosRepository: ProdutosRepository, val categoriasRepository: CategoriasRepository, val locaisRepository: LocaisRepository) : ViewModel(){

    val produtoId = mutableStateOf(0)

    val nomeProduto = mutableStateOf("")

    val codigoProduto = mutableStateOf("")

    val categoriaId = mutableStateOf(0)

    val listaStatus = EnumStatus.values()

    val localId = mutableStateOf(0)

    val statusProduto = mutableStateOf(EnumStatus.FECHADO)

    val quantidade = mutableStateOf(0)

    var categorias = mutableListOf<Categoria>()

    var locais = mutableListOf<Local>()

    val isCadastrado: MutableLiveData<Boolean> = MutableLiveData(false)

    val produtoLocais = mutableStateOf(mutableListOf<ProdutoLocalQuantidade>())

    var deleteDate : Date? = null

    val hasDeleteDate = mutableStateOf(false)

    init{

        viewModelScope.launch {
            locaisRepository.findAll().collect {
                locais = it as MutableList<Local>
            }
        }
        viewModelScope.launch {
            categoriasRepository.findAll().collect {
                categorias = it as MutableList<Categoria>
            }
        }
    }

    fun removeLocal() {
        produtoLocais?.let{ lista ->
            var produtoLocalQuantidade = lista.value.find { it.localId == localId.value }
            if(produtoLocalQuantidade != null) {
                produtoLocais.value.remove(produtoLocalQuantidade)
                quantidade.value = 1
                statusProduto.value = EnumStatus.FECHADO
                localId.value = 0
            }
        }
    }

    fun addlocal(){

        produtoLocais?.let { lista ->

            var produtoLocalQuantidade = lista.value!!.find { it.localId == localId.value }

            if(produtoLocalQuantidade == null) {

                produtoLocalQuantidade = ProdutoLocalQuantidade(
                    produtoId = produtoId.value,
                    localId = localId.value,
                    quantidade = quantidade.value,
                    status = statusProduto.value
                )
                produtoLocais.value.add(produtoLocalQuantidade)
            }else {
                produtoLocalQuantidade.quantidade = quantidade.value
                produtoLocalQuantidade.status = statusProduto.value
            }

            produtoLocalQuantidade.localNome = getNomeLocalById(localId.value)

            quantidade.value = 1
            statusProduto.value = EnumStatus.FECHADO
            localId.value = 0
        }
    }

    fun cadastrar(){

        CoroutineScope(Dispatchers.IO).launch {

            val produto = Produto(
                produtoId = produtoId.value,
                nome = nomeProduto.value,
                codigo = codigoProduto.value,
                categoriaId = categoriaId.value
            )

            produto.locais.addAll(produtoLocais.value!!)

            produtosRepository.insert(produto = produto)
            withContext(Dispatchers.Main){
                isCadastrado.value = true
            }
        }

    }

    fun onChangeNome(newValue: String){
        nomeProduto.value = newValue
    }

    fun onChangeCodigo(newValue: String){
        codigoProduto.value = newValue
    }

    fun onChangeCategoria(newValue: Categoria){
        newValue.let {
            categoriaId.value = it.categoriaId!!
        }
    }

    fun onChangeLocal(newValue: Local){
        newValue.let {
            localId.value = it.localId!!
        }
    }

    fun onChangeLocal(id: Int){
        localId.value = id
    }

    fun onChangeQuantidade(newValue: String){
        val re = Regex("[^0-9]")
        val value = newValue.replace(re, "")
        if (value.isNotBlank()) {
            quantidade.value = value.toInt()
        } else {
            quantidade.value = 0
        }
    }

    fun getNomeCategoriaById(id: Int): String?{
        val categoria = categorias.find { f -> f.categoriaId == id }
        return categoria?.nome
    }

    fun getNomeLocalById(id: Int): String?{
        val local = locais.find { f -> f.localId == id }
        return local?.nome
    }

    fun getCategoriaByNome(nome : String): Categoria?{
        return categorias.first { it.nome == nome }
    }

    fun setCategoriaByNome(nome : String){
        val categoria = getCategoriaByNome(nome)
        categoria?.let{
            onChangeCategoria(categoria)
        }
    }

    fun getLocalByNome(nome : String): Local?{
        return locais.first { it.nome == nome }
    }

    fun setLocalByNome(nome : String){
        val local = getLocalByNome(nome)
        local?.let{
            onChangeLocal(local)
        }
    }

    fun getListaStatus(): List<EnumStatus>{
        return listaStatus.toList()
    }

    fun onChangeStatusProduto(newValue: EnumStatus){
        statusProduto.value = newValue
    }

    fun getLocaisSelecionados(produtoLocais: MutableList<ProdutoLocalQuantidade>): List<Local>{
        return produtoLocais!!.map {
            locais.find { l -> l.localId == it.localId }!!
        }
    }

    fun set(produto: Produto?) {
        if(produto != null && produtoLocais.value.isEmpty()){
            codigoProduto.value = produto.codigo!!
            nomeProduto.value = produto.nome
            categoriaId.value = produto.categoriaId
            produtoId.value = produto.produtoId
            deleteDate = produto.deleteDate
            hasDeleteDate.value = deleteDate != null

            viewModelScope.launch {
                produtosRepository.findLocaisByProdutoId(produto.produtoId).collect{
                    produtoLocais.value = it.map { lwp ->
                            var aux = ProdutoLocalQuantidade(
                                produtoId = produto.produtoId,
                                localId = lwp.local.localId!!,
                                quantidade = lwp.quantidade,
                                status = lwp.status,
                            )
                            aux.localNome = lwp.local.nome
                            aux
                    }.toMutableList()
                }
            }
        }
    }

    fun remover() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                produtosRepository.ativarOrDesativar(produtoId.value)
            }
        }
        deleteDate = if(hasDeleteDate.value) null else Date()
        hasDeleteDate.value = deleteDate != null
    }

    fun isVisible(): Boolean {
        return produtoId.value != 0
    }


}