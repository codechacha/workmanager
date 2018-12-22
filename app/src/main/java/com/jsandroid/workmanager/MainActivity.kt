package com.jsandroid.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager


class MainActivity : AppCompatActivity() {

    private val simpleRequest =
        OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
    private val workManager = WorkManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSimpleWorker.setOnClickListener {
            workManager.beginWith(simpleRequest).enqueue()
        }
    }
}
