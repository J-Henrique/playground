package com.jhbb.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jhbb.rxjava.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnObservable.setOnClickListener { observableClick() }
        binding.btnObservableCreate.setOnClickListener { observableCreateClick() }
    }

    private fun observableClick() {
        Observable.just(
            "Brasil \n",
            "Alemanha \n",
            "Rússia \n",
            "China \n",
            "Austrália \n",
            "Canadá \n")
                .subscribe {
                    binding.txtPanel.append(it.toString())
                }
    }

    private fun observableCreateClick() {
        val countries = listOf(
                "Brasil \n",
                "Alemanha \n",
                "Rússia \n",
                "China \n",
                "Austrália \n",
                "Canadá \n")

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
}