package name.dmx.readhubclient.model

import java.util.*

/**
 * Created by dmx on 17-10-30.
 */
class Topic {
    var id: String = ""
    var createdAt: String = ""
    var order: Long = 0L
    var publishDate: String = ""
    var summary: String = ""
    var title: String = ""
    var updatedAt: String = ""
    var newsArray: Array<News> = emptyArray()
//    var extra: Extra? = null
}