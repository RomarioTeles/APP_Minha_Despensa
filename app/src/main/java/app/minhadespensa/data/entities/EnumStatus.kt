package app.minhadespensa.data.entities

import androidx.compose.ui.text.toLowerCase
import java.util.*

enum class EnumStatus {

    FECHADO, ABERTO, FALTANDO;

    fun capitalizedName() : String {
        return this.name.lowercase().replaceFirstChar { it.uppercase() }
    }
}
