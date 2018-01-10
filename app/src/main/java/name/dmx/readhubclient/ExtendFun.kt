package name.dmx.readhubclient

import android.os.Bundle
import name.dmx.readhubclient.enum.NewsType
import java.text.SimpleDateFormat
import java.util.*

/**
 * 扩展函数
 * Created by dmx on 2018/1/10.
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

fun Bundle.putNewsType(key: String, newsType: NewsType) {
    this.putInt(key, newsType.ordinal)
}

fun Bundle.getNewsType(key: String): NewsType {
    val value = this.getInt(key)
    return when (value) {
        NewsType.TechNews.ordinal -> NewsType.TechNews
        NewsType.DevNews.ordinal -> NewsType.DevNews
        else -> NewsType.TechNews
    }
}