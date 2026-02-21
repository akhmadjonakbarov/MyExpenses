package uz.akbarovdev.myexpenses.core.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return if (map == null) null else gson.toJson(map)
    }

    @TypeConverter
    fun toMap(mapString: String?): Map<String, String>? {
        return if (mapString == null) null else gson.fromJson(
            mapString,
            object : TypeToken<Map<String, String>>() {}.type
        )
    }
}
