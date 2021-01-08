package com.jhbb.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhbb.rxjava.api.Api
import com.jhbb.rxjava.api.CountryService
import com.jhbb.rxjava.api.MockResponse
import com.jhbb.rxjava.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.xml.transform.Transformer

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

        binding.btnSingle200.setOnClickListener { singleGetCountries200() }
        binding.btnSingle500.setOnClickListener { singleGetCountries500() }

        binding.btnCompletable200.setOnClickListener { completableGetCountries200() }
        binding.btnCompletable500.setOnClickListener { completableGetCountries500() }

        binding.btnMaybe200.setOnClickListener { maybeGetCountries200() }
        binding.btnMaybe500.setOnClickListener { maybeGetCountries500() }
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

    private fun singleGetCountries200() {
        service.getCountriesSingle(MockResponse.OK.key)
                .subscribeOn(Schedulers.io())
                .subscribe { countries, error ->
                    countries?.forEach {
                        binding.txtPanel.append(it.name + "\n")
                    }

                    error?.let {
                        binding.txtPanel.append(it.message + "\n")
                    }
                }
    }

    private fun singleGetCountries500() {
        service.getCountriesSingle(MockResponse.SERVER_ERROR.key)
                .subscribeOn(Schedulers.io())
                .doOnError { binding.txtPanel.append(it.message + "\n") }
                .onErrorComplete()
                .subscribe{
                    c -> c.forEach {
                        binding.txtPanel.append(it.name + "\n")
                    }
                }
    }

    private fun completableGetCountries200() {
        service.getCountriesCompletable(MockResponse.OK.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    binding.txtPanel.append("Completed: OK" + "\n")
                }, {
                    binding.txtPanel.append("Completed: Error" + "\n")
                })
    }

    private fun completableGetCountries500() {
        service.getCountriesCompletable(MockResponse.SERVER_ERROR.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    binding.txtPanel.append("Completed: OK" + "\n")
                }, {
                    binding.txtPanel.append("Completed: Error" + "\n")
                })
    }

    private fun maybeGetCountries200() {
        service.getCountriesMaybe(MockResponse.OK.key)
                .subscribeOn(Schedulers.io())
                .subscribe({ countries ->
                    countries?.forEach {
                        binding.txtPanel.append(it.name + "\n")
                    }
                }, {
                    binding.txtPanel.append(it.message + "\n")
                })
    }

    private fun maybeGetCountries500() {
        service.getCountriesMaybe(MockResponse.SERVER_ERROR.key)
                .subscribeOn(Schedulers.io())
                .subscribe({ countries ->
                    countries?.forEach {
                        binding.txtPanel.append(it.name + "\n")
                    }
                }, {
                    binding.txtPanel.append(it.message + "\n")
                })
    }
}