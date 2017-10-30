package name.dmx.readhubclient.model

import java.util.*

/**
 * Created by dmx on 17-10-30.
 */
class Topic {
    var id: Long = 0L
    var createdAt: Date = Date()
    var order: Long = 0L
    var publishDate: Date = Date()
    var summary: String = ""
    var title: String = ""
    var updatedAt: Date = Date()
    var newsArray: Array<News> = emptyArray()
}