package app.minhadespensa.data.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import app.minhadespensa.data.database.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class HiltApplication : Application()

@Module
@InstallIn(SingletonComponent::class)
object DbModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDB
    {
        return Room.databaseBuilder(context, AppDB::class.java, "appminhadespensa.db").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun ProdutosRepository(db: AppDB) = app.minhadespensa.data.repositories.ProdutosRepository(db)

    @Provides
    fun CategoriasRepository(db: AppDB) = app.minhadespensa.data.repositories.CategoriasRepository(db)

    @Provides
    fun LocaisRepository(db: AppDB) = app.minhadespensa.data.repositories.LocaisRepository(db)

    @Provides
    fun ProdutosLocaisQuantidadeRepository(db: AppDB) = app.minhadespensa.data.repositories.ProdutoLocalQuantidadeRepository(db)
}