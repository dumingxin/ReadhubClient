package name.dmx.readhubclient.repository

import android.content.Context
import com.google.gson.GsonBuilder
import com.hzzh.baselibrary.net.DefaultOkHttpClient
import io.reactivex.Observable
import name.dmx.readhubclient.http.Api
import name.dmx.readhubclient.http.PageResult
import name.dmx.readhubclient.model.News
import name.dmx.readhubclient.model.Topic
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dmx on 17-11-7.
 */
class DataRepository private constructor(private val context: Context) {
    private val SERVER_ADDRESS = "https://api.readhub.me/"
    private val httpService: Api

    init {
        val builder = Retrofit.Builder()
        builder.baseUrl(SERVER_ADDRESS)
        builder.client(DefaultOkHttpClient.getOkHttpClient(context))
        builder.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        val retrofit = builder.build()
        httpService = retrofit.create(Api::class.java)
    }

    /**
     * 热门话题
     */

    fun getTopics(lastCursor: Long?, pageSize: Int): Observable<PageResult<Topic>> {
        return httpService.getTopics(lastCursor, pageSize)
    }

    /**
     * 科技动态
     */

    fun getNews(lastCursor: Long?, pageSize: Int): Observable<PageResult<News>> {
        return httpService.getNews(lastCursor, pageSize)
    }

    /**
     * 开发者资讯
     */

    fun getTechNews(lastCursor: Long?, pageSize: Int): Observable<PageResult<News>> {
        return httpService.getTechNews(lastCursor, pageSize)
    }

    companion object {
        private var instance: DataRepository? = null
        fun getInstance(context: Context): DataRepository {
            if (instance == null) {
                synchronized(DataRepository::class.java) {
                    if (instance == null) {
                        instance = DataRepository(context)
                    }
                }
            }
            return instance!!
        }
    }
}