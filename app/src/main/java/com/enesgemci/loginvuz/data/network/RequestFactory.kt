package com.enesgemci.loginvuz.data.network

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RequestFactory<T>(val single: Single<T>) {

    fun buildRequest(
        isConnectedObservable: Observable<Boolean>
    ): Single<T> {
        return isConnectedObservable
            .doOnNext { isConnected ->
                if (!isConnected) {
                    throw NoConnectionException("No internet connection")
                }
            }
            .flatMapSingle { single.compose(applySchedulers()) }
            .singleOrError()
    }

    private fun <T> applySchedulers(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
