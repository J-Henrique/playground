package com.jhbb.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.rxjava.api.Api
import com.jhbb.rxjava.api.CountryService
import com.jhbb.rxjava.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var service: CountryService

    private val countries = listOf(
            "Brasil \n",
            "Alemanha \n",
            "Rússia \n",
            "China \n",
            "Austrália \n",
            "Canadá \n")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = Api.getService()

        binding.btnObservable.setOnClickListener { observableClick() }
        binding.btnObservableCreate.setOnClickListener { observableCreateClick() }
        binding.btnObservableFromCallable.setOnClickListener { observableFromCallableClick() }
        binding.observableFromRange.setOnClickListener { observableFromRangeClick() }

        binding.btnSingle1.setOnClickListener { singleJustClick() }
        binding.btnSingle2.setOnClickListener { singleJustNotBlockingClick() }
        binding.btnSingleRetrofit.setOnClickListener { singleGetCountriesFromApi() }
    }

    private fun observableClick() {
        Observable
            .just(countries)
            .subscribeOn(Schedulers.io())
            .doOnComplete { binding.txtPanel.append("Completed\n") }
            .doOnEach { binding.txtPanel.append("On Each\n") }
            .doOnNext { binding.txtPanel.append("On Next\n") }
            .doAfterTerminate { binding.txtPanel.append("After Terminate\n") }
            .doOnSubscribe { binding.txtPanel.append("Subscribed\n") }
            .doFinally { binding.txtPanel.append("Finally\n") }
            .doOnDispose { binding.txtPanel.append("Dispose\n") }
            .subscribe { c ->
                c.forEach { t ->
                    Thread.sleep(2000)
                    binding.txtPanel.append(t)
                }
            }
    }

    private fun observableCreateClick() {
        val observableCountries = Observable.create<String> {
            emitter ->
                countries.forEachIndexed {
                        index, c ->
                            if (index.equals(3)) {
                                emitter.onError(Exception("Erro"))
                            }
                            emitter.onNext(c)
                }
            emitter.onComplete()
        }

        observableCountries
            .subscribe(
                { binding.txtPanel.append(it.toString()) },
                { binding.txtPanel.append("ERROR") },
                { binding.txtPanel.append("COMPLETED") }
        )
    }

    private fun observableFromCallableClick() {
        Observable
                .fromCallable { countries }
                .subscribe { t -> binding.txtPanel.append(t.toString()) }
    }

    private fun observableFromRangeClick() {
        Observable.rangeLong(1, 15)
                .subscribe { binding.txtPanel.append(it.toString()) }
    }

    private fun singleJustClick() {
        Single
            .just(countries)
            .subscribe { c ->
                c.forEach { t ->
                    Thread.sleep(2000)
                    binding.txtPanel.append(t.toString())
                }
            }
    }

    private fun singleJustNotBlockingClick() {
        Single
            .just(countries)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { binding.txtPanel.append("Success\n") }
            .doAfterSuccess { binding.txtPanel.append("After Success\n") }
            .doAfterTerminate { binding.txtPanel.append("After Terminate\n") }
            .doOnSubscribe { binding.txtPanel.append("Subscribed\n") }
            .doFinally { binding.txtPanel.append("Finally\n") }
            .doOnDispose { binding.txtPanel.append("Dispose\n") }
            .subscribe { c ->
                c.forEach { t ->
                    Thread.sleep(2000)
                    binding.txtPanel.append(t)
                }
            }
    }

    private fun singleGetCountriesFromApi() {
        service.getCountries()
            .subscribeOn(Schedulers.io())
            .subscribe {
                c -> c.forEach {
                    binding.txtPanel.append(it.name + "\n")
                }
            }
    }
}