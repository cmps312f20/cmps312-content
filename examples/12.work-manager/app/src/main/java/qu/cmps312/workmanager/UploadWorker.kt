package qu.cmps312.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params:WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {
        try {
            val count = inputData.getInt(AppKeys.COUNT_VALUE,0)
            for (i in 0 until count) {
                Log.i("UploadWorker", "Uploading $i")
            }

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa")
            val currentDate = time.format(Date())

            val outPutData = Data.Builder()
                                 .putString(AppKeys.CURRENT_DATE, currentDate)
                                 .build()

            return Result.success(outPutData)
        } catch (e:Exception){
            return Result.failure()
        }
    }
}