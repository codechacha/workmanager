package com.jsandroid.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context : Context, params : WorkerParameters)
        : Worker(context, params) {

    override fun doWork(): Result {
        val number = 10
        val result = number * number
        Log.d("SimpleWorker", "SimpleWorker finished: $result")
        return Result.SUCCESS
    }
}