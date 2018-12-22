package com.jsandroid.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager


class MainActivity : AppCompatActivity() {

    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }


    private val simpleRequest =
        OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
    private val workManager = WorkManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSimpleWorkerBtn.setOnClickListener {
            workManager.beginWith(simpleRequest).enqueue()
            val status = workManager.getWorkInfoByIdLiveData(simpleRequest.id)
            status.observe(this, Observer<WorkInfo> { info ->
                val workFinished = info?.state?.isFinished == true
                workStatusText.text = when {
                    workFinished -> "work status: finished"
                    else -> "work status: running"
                }
            })
        }
    }
}
