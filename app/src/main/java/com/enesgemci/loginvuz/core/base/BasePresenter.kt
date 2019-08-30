package com.enesgemci.loginvuz.core.base

import com.enesgemci.loginvuz.data.network.RequestFactory
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

abstract class BasePresenter<V : BaseView> : MvpBasePresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    private val isConnected: Observable<Boolean>
        get() = Observable.defer { Observable.just(view?.isConnected) }

    protected fun <T> Single<T>.call(
        onSuccess: (response: T) -> Unit,
        onError: (error: String) -> Unit
    ) {
        val requestFactory = RequestFactory(this)

        val request = requestFactory.buildRequest(isConnected)

        val successConsumer: Consumer<T> = Consumer {
            onSuccess(it)
        }
        val errorConsumer: Consumer<Throwable> = Consumer {
            onError(it.message.orEmpty())
        }

        compositeDisposable.add((request.subscribe(successConsumer, errorConsumer)))
    }

    private fun showLoading() {
        ifViewAttached {
            it.showLoadingDialog()
        }
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }
}