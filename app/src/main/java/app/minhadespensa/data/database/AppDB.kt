package app.minhadespensa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.minhadespensa.data.dao.CategoriasDAO
import app.minhadespensa.data.dao.LocaisDAO
import app.minhadespensa.data.dao.ProdutosDAO
import app.minhadespensa.data.dao.ProdutoLocalQuantidadeDAO
import app.minhadespensa.data.entities.*

@Database(
    entities = [
        Produto::class,
        Categoria::class,
        Local::class,
        ProdutoLocalQuantidade::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun produtosDAO(): ProdutosDAO
    abstract fun categoriasDAO(): CategoriasDAO
    abstract fun locaisDAO(): LocaisDAO
    abstract fun produtosLocalQuantidadeDAO(): ProdutoLocalQuantidadeDAO
}