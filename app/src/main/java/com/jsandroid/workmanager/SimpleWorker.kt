package com.jsandroid.workmanager

import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context : Context, params : WorkerParameters)
        : Worker(context, params) {

    companion object {
        const val EXTRA_NUMBER = "EXTRA_NUMBER"
        const val EXTRA_RESULT = "EXTRA_RESULT"
    }

    override fun doWork(): Result {
        val number = inputData.getInt(EXTRA_NUMBER, 0)
        val result = number * number
        SystemClock.sleep(5000)
        Log.d("SimpleWorker", "SimpleWorker finished: $result")

        val outputData = Data.Builder()
            .putInt(SimpleWorker.EXTRA_RESULT, result)
            .build()

        return Result.success(outputData)
    }
}