package com.jsandroid.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val workManager = WorkManager.getInstance()

        startSimpleWorkerBtn.setOnClickListener {
            val inputData = Data.Builder()
                .putInt(SimpleWorker.EXTRA_NUMBER, 5)
                .build()

            val constraints = Constraints.Builder()
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
                .build()

            val simpleRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .setInputData(inputData)
                .addTag(WORK_TAG)
                .setConstraints(constraints)
                .build()

            val simple2Request = OneTimeWorkRequest.Builder(Simple2Worker::class.java)
                .setInputData(inputData)
                .addTag(WORK_TAG)
                .setConstraints(constraints)
                .build()

            workManager
                .beginWith(simpleRequest)
                .then(simple2Request)
                .enqueue()

            val status = workManager.getWorkInfoByIdLiveData(simpleRequest.id)
            status.observe(this, Observer<WorkInfo> { info ->
                val workFinished = info!!.state?.isFinished
                val result = info?.outputData?.getInt(SimpleWorker.EXTRA_RESULT, 0)
                workStatusText.text = when (info.state) {
                    WorkInfo.State.RUNNING,
                    WorkInfo.State.ENQUEUED -> {
                        "work status: ${info.state}"
                    }
                    WorkInfo.State.CANCELLED -> {
                        "work status: ${info.state}, finished: $workFinished"
                    }
                    WorkInfo.State.SUCCEEDED,
                    WorkInfo.State.FAILED-> {
                        "work status: ${info.state}, result: ${result}, finished: $workFinished"
                    }
                    else -> {
                        "work status: running"
                    }
                }
            })
        }

        cancelSimpleWorkerBtn.setOnClickListener {
            val workManager = WorkManager.getInstance()
            workManager.cancelAllWorkByTag(WORK_TAG)
            // workManager.cancelWorkById(simpleRequest.id)
            // workManager.cancelAllWork()
        }




    }
}
