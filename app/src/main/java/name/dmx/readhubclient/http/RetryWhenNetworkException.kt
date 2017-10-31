package com.hzzh.baselibrary.net

import io.reactivex.Observable
import io.reactivex.exceptions.CompositeException
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * Created by dmx on 2016/12/14.
 */

class RetryWhenNetworkException : Function<Observable<out Throwable>, Observable<*>> {

    private var count = 3
    private var delay: Long = 1000
    private var increaseDelay: Long = 1000

    constructor(count: Int, delay: Long) {
        this.count = count
        this.delay = delay
    }

    constructor(count: Int, delay: Long, increaseDelay: Long) {
        this.count = count
        this.delay = delay
        this.increaseDelay = increaseDelay
    }

    @Throws(Exception::class)
    override fun apply(observable: Observable<out Throwable>): Observable<*> {
        return observable
                .zipWith(Observable.range(1, count + 1), BiFunction<Throwable, Int, Wrapper> { t1, t2 -> Wrapper(t1, t2) })
                .flatMap(Function<Wrapper, Observable<*>> { wrapper ->
                    if ((wrapper.throwable is ConnectException
                            || wrapper.throwable is SocketTimeoutException
                            || wrapper.throwable is TimeoutException) && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                        return@Function Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS)
                    }
                    Observable.error<Throwable>(wrapper.throwable!!)
                })
    }


    private inner class Wrapper(throwable: Throwable, val index: Int) {
        var throwable: Throwable? = null

        init {
            if (throwable is CompositeException) {
                this.throwable = throwable.exceptions[0]
            } else {
                this.throwable = throwable
            }

        }
    }

}
