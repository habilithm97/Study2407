package com.example.rxjava

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rxjava.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {
        binding.apply {
            btn.setOnClickListener {
                val observable = Observable.just("Hello RxJava!") // 단일 항목을 발행하는 Observable 객체 생성

                val observer = object : DisposableObserver<String>() { // Observer 객체 생성(Observable에서 발행하는 항목을 처리)
                    // Observable이 새로운 데이터를 방출할 때마다 호출
                    override fun onNext(t: String) {
                        tv.text = t
                    }
                    override fun onError(e: Throwable) {
                        tv.text = "Error : ${e.message}"
                    }
                    override fun onComplete() {}
                }
                val disposable = observable // Disposable 객체 반환 -> 구독 취소 및 리소스 정리 가능
                    .subscribeOn(Schedulers.io()) // I/O 스레드에서 실행
                    .observeOn(AndroidSchedulers.mainThread()) // observer는 메인 스레드에서 실행(안전한 UI 업데이트)
                    .subscribeWith(observer) // observer(observable이 발행하는 데이터를 수신 및 처리)와 연결하여 구독 시작
                disposables.add(disposable) // 한 번에 모든 구독 취소 가능 -> 리소스 관리 용이
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // 액티비티가 파괴될 때 모든 구독 해제 -> 메모리 릭 방지
    }
}