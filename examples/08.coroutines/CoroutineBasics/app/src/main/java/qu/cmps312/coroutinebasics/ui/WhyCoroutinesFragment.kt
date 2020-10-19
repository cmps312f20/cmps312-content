package qu.cmps312.coroutinebasics.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coroutinebasics.R
import kotlinx.android.synthetic.main.fragment_why_coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.util.*
import kotlin.concurrent.thread

class WhyCoroutinesFragment : Fragment(R.layout.fragment_why_coroutines) {
    private val TAG = "WhyCoroutinesFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var score = 0
        incrementBtn.setOnClickListener {
            score++
            scoreTv.text = score.toString()
        }

        // Synchronous blocking call
        mainThreadBtn.setOnClickListener {
            resultTv.text = ""
            Log.i(TAG, "Running on ${Thread.currentThread()} thread.")
            resultTv.text = nextProbablePrime().toString()
        }

        // Callback version
        backgroudThreadBtn.setOnClickListener {
            resultTv.text = ""
            getPrimeBigInt { result ->
                requireActivity().runOnUiThread {
                    Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
                    resultTv.text = result.toString()
                }
            }
        }

        // Coroutine version
        coroutineBtn.setOnClickListener {
            resultTv.text = ""
            val job = lifecycleScope.launch {
                val result = getPrimeBigInt()
                resultTv.text = result.toString()
            }
            // job.cancel() can be used to cancel the job
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

    // Coroutine version
    private suspend fun getPrimeBigInt() = withContext(Dispatchers.Default) {
        Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
        val result = nextProbablePrime()
        return@withContext result
    }

    // Callback version
    private fun getPrimeBigInt(callBack: (BigInteger) -> Unit) {
        thread {
            Log.i(TAG, "Running on ${Thread.currentThread().name} thread.")
            val result =  nextProbablePrime()

            callBack(result)
            // But trying to access the UI from this backgroud thread will cause an error
            //resultTv.text = result.toString()
        }
    }
}