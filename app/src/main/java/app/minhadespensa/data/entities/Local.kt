package app.minhadespensa.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "locais")
data class Local (
    @PrimaryKey(autoGenerate = true) var localId: Int? = null,
    var nome: String
): Parcelable