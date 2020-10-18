package qu.cmps312.coroutinebasics.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.coroutinebasics.R
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_why_coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.util.*
import kotlin.concurrent.thread

class WhyCoroutinesFragment : Fragment(R.layout.fragment_why_coroutines) {
    private val TAG = "Coroutines"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = TextView(requireContext())
        tv.text = "Qatar"
        oFlow.addView(tv)


        var score = 0
        incrementBtn.setOnClickListener {
            score++
            scoreTv.text = score.toString()
        }

        longRunningTaskBtn.setOnClickListener {
            Log.i(TAG, "Running on ${Thread.currentThread()} thread.")
            resultSyncTv.text = nextProbablePrime().toString()
        }

        longRunningTaskOnBackgroudThreadBtn.setOnClickListener {
            val job = lifecycleScope.launch {
                val result = longRuning()
                withContext(Dispatchers.Main) {
                    resultTv.text = result.toString()
                }
            }
            //job.cancel()
            // Call the async long running task and pass to it a callback
            /*longRuningAsync { result ->
                requireActivity().runOnUiThread {
                    Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
                    resultTv.text = result.toString()
                }
            }
        }*/
        }
    }

    // Example realistic long running computation
    // Calculate the next probable prime based on a randomly generated BigInteger
    // which happens to be a fairly expensive task (since this calculation is based
    // on a random it will not run in deterministic time)
    private fun nextProbablePrime(): BigInteger {
        //Just pretend to work hard for 5 seconds
        //Thread.sleep(5000)
        return BigInteger(1500, Random()).nextProbablePrime()
    }

    private suspend fun longRuning() = withContext(Dispatchers.Default) {
            Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
            val result =  nextProbablePrime()
            return@withContext result
            //cb(result)
            // But trying to access the UI from this backgroud thread will cause an error
            //resultTv.text = result.toString()
        }
    //}

    private fun longRuningAsync(cb: (BigInteger) -> Unit) {
        thread {
            Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
            val result =  nextProbablePrime()

            cb(result)
            // But trying to access the UI from this backgroud thread will cause an error
            //resultTv.text = result.toString()
        }
    }
}