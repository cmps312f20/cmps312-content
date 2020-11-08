package qu.cmps312.workmanager

import android.app.Application
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

        val inputData = workDataOf(AppKeys.COUNT_VALUE to 125)

        val constraints = Constraints.Builder()
                                     //.setRequiresDeviceIdle(true)
                                     //.setRequiresCharging(true)
                                     .setRequiredNetworkType(NetworkType.CONNECTED)
                                     .build()

        val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                            .setConstraints(constraints)
                            .setInputData(inputData)
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

        /*
        WorkManager.getInstance(applicationContext).getWorkInfosByTag("Sync")
        WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWork("MyUniqueName")
        WorkManager.getInstance(applicationContext).getWorkInfoById(uploadRequest.id)
        WorkManager.getInstance(applicationContext).getWorkInfosByTag(TAG_SAVE)
        WorkManager.getInstance(applicationContext).getWorkInfosByTagLiveData(TAG_SAVE)

        WorkManager.getInstance(applicationContext).cancelAllWork()
        */

        //Source: developer.android.com/topic/libraries/architecture/workmanager/how-to/cancel-stop-work
        /*
        val saveImageWorkRequest = OneTimeWorkRequestBuilder<SaveImageWorker>()
                .addTag(TAG_SAVE)
                .build()

        WorkManager.getInstance(applicationContext).cancelWorkById(saveImageWorkRequest.id)
        WorkManager.getInstance(applicationContext).cancelAllWorkByTag(TAG_SAVE) */

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
        // Create a periodic work request with 30 mins as repeat interval
        val repeatInterval = 30
        val periodicWorkRequest = PeriodicWorkRequestBuilder<DownloadWorker>(30, TimeUnit.MINUTES)
                                    .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }

    private fun getWorkerConstraints(): Constraints {
        return Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(false)
                .setRequiresStorageNotLow(false)
                .build()
    }


}

/*
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val backupWorkRequest = PeriodicWorkRequestBuilder<BackupWorker>(8, TimeUnit.HOURS)
                                        .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                "BackupWork",
                ExistingPeriodicWorkPolicy.KEEP,
                backupWorkRequest)

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.getWorkInfosForUniqueWorkLiveData("BackupWork")
    }
}
*/