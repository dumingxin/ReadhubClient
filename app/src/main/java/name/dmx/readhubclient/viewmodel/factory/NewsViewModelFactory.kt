package name.dmx.readhubclient.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import name.dmx.readhubclient.enum.NewsType
import name.dmx.readhubclient.viewmodel.NewsViewModel


/**
 * 创建带构造参数的ViewModel
 * Created by dmx on 2018/1/10.
 */
class NewsViewModelFactory(private val newsType: NewsType, private val pageSize: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsType, pageSize) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}