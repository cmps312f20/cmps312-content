package com.example.coroutinebasics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    var nextValue = MutableLiveData<Long>()
    var job : Job? = null

    fun startFibonacci() {
        stopFibonacci()
        job = viewModelScope.launch {
            fibonacci()
        }
    }

    fun stopFibonacci() {
        job?.let {
            if (it.isActive)
                it.cancel()
        }
    }

    suspend fun fibonacci() = withContext(Dispatchers.IO) {
        try {
            var terms = Pair(0L, 1L)
            // this sequence is infinite
            while (true) {
                //yield()  // periodic check - if job cancelled exit the loop
                //ensureActive()
                if (!isActive) return@withContext
                //println("${terms.first}")
                withContext(Dispatchers.Main) {
                    nextValue.value = terms.first
                }
                terms = Pair(terms.second, terms.first + terms.second)
                // Suspend the function for 200ms
                delay(400)
            }
            println("Job done!")
        } catch (e: CancellationException) {
            println("Job cancelled!")
        }
    }
}