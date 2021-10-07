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
import javax.inject.Inject

@HiltViewModel
class TelaCadastroProdutoViewModel @Inject constructor(val produtosRepository: ProdutosRepository, val categoriasRepository: CategoriasRepository, val locaisRepository: LocaisRepository) : ViewModel(){

    val nomeProduto = mutableStateOf("")

    val codigoProduto = mutableStateOf("")

    val localId = mutableStateOf(0)

    val categoriaId = mutableStateOf(0)

    val quantidade = mutableStateOf(0)

    val listaStatus = EnumStatus.values()

    val statusProduto = mutableStateOf(EnumStatus.FECHADO)

    var categorias = mutableListOf<Categoria>()

    var locais = mutableListOf<Local>()

    val isCadastrado: MutableLiveData<Boolean> = MutableLiveData(false)

    val produtoLocais : MutableLiveData<MutableList<ProdutoLocalQuantidade>> = MutableLiveData(mutableListOf())

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

    fun addlocal(){

        produtoLocais?.let { lista ->

            var produtoLocalQuantidade = lista.value!!.find { it.localId == localId.value }

            if(produtoLocalQuantidade == null) {

                produtoLocalQuantidade = ProdutoLocalQuantidade(
                    produtoId = localId.value,
                    localId = localId.value,
                    quantidade = quantidade.value,
                    status = statusProduto.value
                )
                lista.value!!.add(produtoLocalQuantidade)
            }else {
                produtoLocalQuantidade.quantidade = quantidade.value
                produtoLocalQuantidade.status = statusProduto.value
            }

            quantidade.value = 1
            statusProduto.value = EnumStatus.FECHADO
        }
    }

    fun cadastrar(){

        CoroutineScope(Dispatchers.IO).launch {

            val produto = Produto(
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

    fun getLocaisSelecionados(): List<Local>{
        return produtoLocais.value!!.map {
            locais.find { l -> l.localId == it.localId }!!
        }
    }

}