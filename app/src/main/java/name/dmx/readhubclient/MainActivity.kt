package name.dmx.readhubclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
        val observable = DataRepository.getService(this).getTopics(null, 10)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object : Observer<PageResult<Topic>> {
            override fun onError(e: Throwable) {
//                    Toast.makeText(this@MainActivity, "onError", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onComplete() {

            }

            override fun onNext(t: PageResult<Topic>) {
//                    Toast.makeText(this@MainActivity, "onNext", Toast.LENGTH_LONG).show()
            }
        })


    }
}
