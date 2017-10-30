package name.dmx.readhubclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hzzh.baselibrary.net.transformer.SchedulerTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import name.dmx.readhubclient.http.DataRepository
import name.dmx.readhubclient.http.PageResult
import name.dmx.readhubclient.model.Topic

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()
        Thread(Runnable {
            val observable = DataRepository.getService().getTopics(null, 10)
            observable.compose(SchedulerTransformer<PageResult<Topic>>())
            observable.subscribe(object : Observer<PageResult<Topic>> {
                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, "onError", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: PageResult<Topic>) {
                    Toast.makeText(this@MainActivity, "onNext", Toast.LENGTH_LONG).show()
                }
            })
        }).start()

    }
}
