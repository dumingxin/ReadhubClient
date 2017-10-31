package name.dmx.readhubclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hzzh.baselibrary.net.transformer.SchedulerTransformer
import name.dmx.readhubclient.http.DataRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()
        val observable = DataRepository.getService(this).getTopics(null, 10).compose(SchedulerTransformer())
        observable.subscribe({ data ->
            data.data
        }, { error ->
            error.printStackTrace()
        })


    }
}
