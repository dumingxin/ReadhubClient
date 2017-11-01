package name.dmx.readhubclient

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by dmx on 17-11-1.
 */
object DateUtil {
    fun str2Date(str: String, pattern: String): Date? {
        try {
            val sdf = SimpleDateFormat(pattern)
            val date = sdf.parse(str)
            date.time = date.time + 8 * 60 * 60 * 1000
            return date
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}