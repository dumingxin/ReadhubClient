package name.dmx.readhubclient.http

/**
 * Created by dmx on 17-10-30.
 */
class PageResult<T> {
    var pageSize = 0
    var totalItems = 0L
    var totalPages = 0
    var data: List<T>? = null
}