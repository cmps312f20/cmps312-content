package com.example.coroutinebasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val  TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var score = 0
        incrementBtn.setOnClickListener {
            score++
            scoreTv.text = score.toString()
        }
        longRunningBtn.setOnClickListener {
            Log.i(TAG,"${Thread.currentThread()} is the main thread.")
            //fibonacci(1_000_000)
            thread {
                Log.i(TAG,"${Thread.currentThread()} has run.")
                fibonacci(1_000_000)
                //scoreTv.text = (score++).toString()
            }
            /*
            suspend fun getNews() {
	            val news = api.fetchNews()	 // Background thread
                withContext(Dispatchers.Main) {
	                displayNews(user)	    // UI thread
                }
            }
            */
           /* var data = api.getData()
            display(data) */
            /*var data
            thread {
                data = api.getData()
            }
            render(data) */

            GlobalScope.launch  {
                Log.i(TAG,"${Thread.currentThread()} has run.")
                fibonacci(1_000_000)
                withContext(Dispatchers.Main) {
                    Log.i(TAG,"${Thread.currentThread()} updating UI.")
                    scoreTv.text = (score++).toString()
                }
            }

            //fibonacci(100_000)
            /*GlobalScope.launch {
                Log.i(TAG, "${Thread.currentThread()} has run.")
                fibonacci(100_000)
                //longRunningTask()
            }*/
        }
    }

    fun longRunningTask() {
        //Just pretend to work hard
        Thread.sleep(1 * 60 * 1000)
    }

    /*
       The Fibonacci series is a series where the next term is the sum of pervious two terms.
       Fn = Fn-1 + Fn-2
       The first two terms of the Fibonacci sequence is 0 followed by 1.
       The Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
     */
    private fun fibonacci(n: Int) : List<Long> {
        var t1 = 0L
        var t2 = 1L
        val fibonacciList = mutableListOf<Long>(t1, t2)
        //Log.d(TAG, "$0 -> $t1")
        //Log.d(TAG, "$1 -> $t2")

        //Log.d(TAG, "First $n terms: ")
        for (i in 1 until n) {
            val sum = t1 + t2
            t1 = t2
            t2 = sum

            //Log.d(TAG, "${i+1} -> $sum")
            fibonacciList.add(sum)
        }
        return fibonacciList
    }
}