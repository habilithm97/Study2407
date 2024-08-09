package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Thread.sleep

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        for(i in 1..100) {
            Log.d("MyWorker", i.toString())
            sleep(1000)
        }
        return Result.success()
    }
}