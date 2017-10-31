package name.dmx.readhubclient.http

import android.content.Context
import com.google.gson.GsonBuilder
import com.hzzh.baselibrary.net.DefaultOkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dmx on 17-10-30.
 */
object DataRepository {
    private val SERVER_ADDRESS = "https://api.readhub.me/"
    private var service: Api? = null


    fun getService(context: Context): Api {
        if (service == null) {
            val builder = Retrofit.Builder()
            builder.baseUrl(SERVER_ADDRESS)
            builder.client(DefaultOkHttpClient.getOkHttpClient(context))
            builder.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            val retrofit = builder.build()
            service = retrofit.create(Api::class.java)
        }
        return service!!
    }
}