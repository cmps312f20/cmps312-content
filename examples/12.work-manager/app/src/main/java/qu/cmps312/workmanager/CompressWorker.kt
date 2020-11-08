package qu.cmps312.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CompressWorker(context: Context, params:WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {
        try {
            for (i in 0 ..300) {
                Log.i("CompressWorker", "Compressing $i")
            }
            return Result.success()
        } catch (e:Exception){
            return Result.failure()
        }
    }
}