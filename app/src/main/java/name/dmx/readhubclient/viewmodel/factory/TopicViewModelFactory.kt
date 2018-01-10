package name.dmx.readhubclient.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import name.dmx.readhubclient.viewmodel.TopicViewModel

/**
 * 创建带构造参数的ViewModel
 * Created by dmx on 2018/1/10.
 */
class TopicViewModelFactory(private val pageSize: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicViewModel::class.java)) {
            return TopicViewModel(pageSize) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}