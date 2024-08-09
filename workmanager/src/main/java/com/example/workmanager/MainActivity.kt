package com.example.workmanager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.workmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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
        binding.btn.setOnClickListener {
            // 한 번만 실행되는 OneTimeWorkRequest
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

            // 여러 번 실행되는 PeriodicWorkRequest
            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                MyWorker::class.java,
                15,
                TimeUnit.MINUTES
            ).build()

            // WorkManager는 싱글톤으로 관리되며, 앱의 Context를 통해 인스턴스를 가져올 수 있음
            // 작업 예약 후 실행 조건이 충족되면 실행
            //WorkManager.getInstance(this@MainActivity).enqueue(oneTimeWorkRequest)
            WorkManager.getInstance(this@MainActivity).enqueue(periodicWorkRequest)
        }
    }
}