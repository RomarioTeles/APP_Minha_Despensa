package app.minhadespensa.data.entities

import androidx.compose.ui.text.capitalize
import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromEnumStatus(value: EnumStatus?): String? {
        return value?.let { value.name.lowercase().replaceFirstChar{it.uppercase()} }
    }

    @TypeConverter
    fun stringToEnumStatus(value: String?): EnumStatus? {
        return value?.let{ EnumStatus.valueOf(value.uppercase()) }
    }
}