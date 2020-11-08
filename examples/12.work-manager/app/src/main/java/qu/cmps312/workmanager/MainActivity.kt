package qu.cmps312.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            setOneTimeWorkRequest()
            //setPeriodicWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)

        val data: Data = Data.Builder()
                             .putInt(AppKeys.COUNT_VALUE, 125)
                             .build()

        val constraints = Constraints.Builder()
                                     //.setRequiresDeviceIdle(true)
                                     //.setRequiresCharging(true)
                                     .setRequiredNetworkType(NetworkType.CONNECTED)
                                     .build()

        val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                            .setConstraints(constraints)
                            .setInputData(data)
                            .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                            .build()

        val filterRequest = OneTimeWorkRequestBuilder<FilterWorker>().build()

        val compressRequest= OneTimeWorkRequestBuilder<CompressWorker>().build()

        val downloadRequest= OneTimeWorkRequestBuilder<DownloadWorker>().build()
        //WorkManager.getInstance(applicationContext).enqueue(downloadRequest)

        val parallelWorks = listOf(downloadRequest, filterRequest)
       /* workManager.beginWith(parallelWorks)
                   .then(compressRequest)
                   .then(uploadRequest)
                   .enqueue()*/

        WorkManager.getInstance(applicationContext).enqueue(uploadRequest)

        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
                   .observe(this, Observer {
                        textView.text = it.state.name
                        if(it.state.isFinished) {
                            val data = it.outputData
                            val message = data.getString(AppKeys.CURRENT_DATE)
                            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                        }
                   })
    }

    private fun setPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<DownloadWorker>(1, TimeUnit.MINUTES)
                                    .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }
}
