package name.dmx.readhubclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hzzh.baselibrary.net.transformer.SchedulerTransformer
import name.dmx.readhubclient.MyApplication
import name.dmx.readhubclient.model.Topic
import name.dmx.readhubclient.repository.DataRepository

/**
 * Created by dmx on 17-11-7.
 */
class TopicViewModel : ViewModel() {
    private val liveData: MutableLiveData<List<Topic>> = MutableLiveData()
    private var isFirstPage = true
    private var lastCursor: Long? = null
    private var pageSize: Int = 0
    private val topicList = ArrayList<Topic>()
    fun getLiveData(pageSize: Int): LiveData<List<Topic>> {
        this.pageSize = pageSize
        fetchData()
        return liveData
    }

    fun refresh() {
        isFirstPage = true
        lastCursor = null
        fetchData()
    }

    fun loadMore() {
        isFirstPage = false
        fetchData()
    }

    private fun fetchData() {
        val observable = DataRepository.getInstance(MyApplication.instance).getTopics(lastCursor, pageSize)
        observable.compose(SchedulerTransformer())
                .subscribe({ data ->
                    if (isFirstPage) {
                        topicList.clear()
                    }
                    topicList.addAll(topicList.size, data.data?.toList()!!)
                    liveData.value = topicList
                    lastCursor = data.data?.last()?.order
                }, {
                    liveData.value = null
                })
    }
}