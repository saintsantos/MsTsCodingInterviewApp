package com.saintsantos.mstscodinginterview

import androidx.lifecycle.*
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class PhotoViewModel : ViewModel(), LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val apiPath: String = ""
    val moshi = Moshi.Builder().build()
    var compilationApi: CompilationApi

    var liveData: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "Do cool stuff here"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiPath)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        compilationApi = retrofit.create(CompilationApi::class.java)
    }

    fun callToCompilationApi() {
        compositeDisposable.add(
            compilationApi.postPhotoToApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({compResponse ->
                    // Handle success here
                    liveData.value = "It worked yo"
                }, {compError ->
                    //Handle error here
                    liveData.value = "It failed yo"
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        compositeDisposable.clear()
    }
}