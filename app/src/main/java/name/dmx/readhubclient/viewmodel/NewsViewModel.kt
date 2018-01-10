package name.dmx.readhubclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hzzh.baselibrary.net.transformer.SchedulerTransformer
import name.dmx.readhubclient.MyApplication
import name.dmx.readhubclient.enum.NewsType
import name.dmx.readhubclient.model.News
import name.dmx.readhubclient.repository.DataRepository
import name.dmx.readhubclient.toDate

/**
 * Created by dmx on 17-11-7.
 */
class NewsViewModel(private val newsType: NewsType, private val pageSize:Int) : ViewModel() {

    private val liveData: MutableLiveData<List<News>> = MutableLiveData()
    private var isFirstPage = true
    private var lastCursor: Long = 0L
    private val newsList = ArrayList<News>()
    fun getLiveData(): LiveData<List<News>> {
        lastCursor = System.currentTimeMillis()
        fetchData()
        return liveData
    }

    fun refresh() {
        isFirstPage = true
        lastCursor = System.currentTimeMillis()
        fetchData()
    }

    fun loadMore() {
        isFirstPage = false
        fetchData()
    }

    private fun fetchData() {
        val observable = if (newsType == NewsType.TechNews) {
            DataRepository.getInstance(MyApplication.instance).getNews(lastCursor, pageSize)
        } else {
            DataRepository.getInstance(MyApplication.instance).getTechNews(lastCursor, pageSize)
        }
        observable.compose(SchedulerTransformer())
                .subscribe({ data ->
                    if (isFirstPage) {
                        newsList.clear()
                    }
                    newsList.addAll(newsList.size, data.data?.toList()!!)
                    liveData.value = newsList
                    lastCursor = data.data?.last()?.publishDate!!.toDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")?.time!!
                }, {
                    liveData.value = null
                })
    }
}