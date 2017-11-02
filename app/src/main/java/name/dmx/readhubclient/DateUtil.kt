package name.dmx.readhubclient

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by dmx on 17-11-1.
 */
fun String.toDate(pattern: String): Date? {
    try {
        val sdf = SimpleDateFormat(pattern, Locale.CHINA)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.parse(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}