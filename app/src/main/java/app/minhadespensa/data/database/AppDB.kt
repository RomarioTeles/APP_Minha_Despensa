package app.minhadespensa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.minhadespensa.data.dao.CategoriasDAO
import app.minhadespensa.data.dao.LocaisDAO
import app.minhadespensa.data.dao.ProdutosDAO
import app.minhadespensa.data.dao.ProdutoLocalQuantidadeDAO
import app.minhadespensa.data.entities.Categoria
import app.minhadespensa.data.entities.Local
import app.minhadespensa.data.entities.Produto
import app.minhadespensa.data.entities.ProdutoLocalQuantidade

@Database(
    entities = [
        Produto::class,
        Categoria::class,
        Local::class,
        ProdutoLocalQuantidade::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun produtosDAO(): ProdutosDAO
    abstract fun categoriasDAO(): CategoriasDAO
    abstract fun locaisDAO(): LocaisDAO
    abstract fun produtosLocalQuantidadeDAO(): ProdutoLocalQuantidadeDAO
}