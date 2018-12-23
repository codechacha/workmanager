package com.jsandroid.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Data
import kotlinx.android.synthetic.main.activity_main.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager


class MainActivity : AppCompatActivity() {

    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSimpleWorkerBtn.setOnClickListener {
            val inputData = Data.Builder()
                .putInt(SimpleWorker.EXTRA_NUMBER, 5)
                .build()

            val simpleRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .setInputData(inputData)
                .build()

            val workManager = WorkManager.getInstance()
            workManager.beginWith(simpleRequest).enqueue()

            val status = workManager.getWorkInfoByIdLiveData(simpleRequest.id)
            status.observe(this, Observer<WorkInfo> { info ->
                val workFinished = info?.state?.isFinished == true
                val result = info?.outputData?.getInt(SimpleWorker.EXTRA_RESULT, 0)
                workStatusText.text = when {
                    workFinished -> "work status: finished, result: ${result}"
                    else -> "work status: running"
                }
            })
        }




    }
}
