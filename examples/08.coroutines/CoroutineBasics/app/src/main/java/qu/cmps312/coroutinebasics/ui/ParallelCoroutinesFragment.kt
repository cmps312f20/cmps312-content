package qu.cmps312.coroutinebasics.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.example.coroutinebasics.R
import kotlinx.android.synthetic.main.fragment_parallel_coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel
import qu.cmps312.coroutinebasics.ui.viewmodel.StockQuote

class ParallelCoroutinesFragment : Fragment(R.layout.fragment_parallel_coroutines) {
    private val viewModel by activityViewModels<MainViewModel>()

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        priceTv.text = "failed"
        val msg = "Exception thrown somewhere within parent or child: $exception."
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
        println("Debug: $msg")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCompanySpinner()
        initCompaniesAutoCompleteTv()
        companiesTv.setText("Tesla, Apple, Microsoft, IBM, ")

        var score = 0
        incrementBtn.setOnClickListener {
            score++
            scoreTv.text = score.toString()
        }

        getStockPriceBtn.setOnClickListener {
            priceTv.text = "In progress..."
            lifecycleScope.launch(exceptionHandler)  {
                val quote = viewModel.getStockQuote(companySp.selectedItem.toString())
                priceTv.text = "${quote.name} (${quote.symbol}) = ${quote.price}"
            }
        }

        getStockPricesBtn.setOnClickListener {
            resultsTv.text = ""
            executionTimeTv.text = "In progress..."
            val companies = companiesTv.text.toString().trim().removeTrailingComma().split(",")

            val startTime = System.currentTimeMillis()
            val parentJob = lifecycleScope.launch(exceptionHandler) {
                println(">>> Debug: parentJob = lifecycleScope.launch Running on ${Thread.currentThread().name} thread.")
                if (parallelSwitch.isChecked)
                    processInParallel(companies)
                else
                    processSequentially(companies)
            }

            parentJob.invokeOnCompletion {
                val executionDuration = System.currentTimeMillis() - startTime
                executionTimeTv.text = executionDuration.toString()
                println(">>> Debug: Parent job done. Total elapse time $executionDuration")
            }
        }
    }

    suspend fun processInParallel(companies: List<String>) = withContext(Dispatchers.Default) {
        println(">>> Debug: processInParallel Running on ${Thread.currentThread().name} thread.")
        // Get stock quotes in parallel
        val deferred = mutableListOf<Deferred<StockQuote>>()
        for(company in companies)
            deferred.add( async { viewModel.getStockQuote(company) } )

        // Await the results and when ready display them
        deferred.forEach {
            //Can be cancelled if needed using it.cancel()
            val quote = it.await()
            val result = "${quote.name} (${quote.symbol}) = ${quote.price}"
            displayResult(result)
        }
    }

    suspend fun processSequentially(companies: List<String>) = withContext(Dispatchers.Default) {
        println("Debug: processSequentially Running on ${Thread.currentThread().name} thread.")
        companies.forEach {
            val quote = viewModel.getStockQuote(it)
            val result = "${quote.name} (${quote.symbol}) = ${quote.price}"
            displayResult(result)
        }
    }

    private suspend fun displayResult (result: String) {
        withContext(Dispatchers.Main) {
            resultsTv.text = "${resultsTv.text} \n$result"
        }
    }

    private fun toString(companies : List<String>, prices : List<Int>) =
        companies.mapIndexed { idx, company -> "$company = ${prices[idx]}" }
                 .joinToString("\n")


    private fun initCompanySpinner() {
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            viewModel.companies.keys.toList()
        )
        companySp.adapter = adapter
    }

    fun String.removeTrailingComma() =
        this.replace("(?!^),+$".toRegex(), "")

    private fun initCompaniesAutoCompleteTv() {
        // It tells the AutoCompleteTextView what layout to use for individual suggestions
        // android.R.layout.simple_list_item_1 => it a built-in layout. It has 1 TextView
        // List of all android built-in layout available @
        // https://github.com/aosp-mirror/platform_frameworks_base/tree/master/core/res/res/layout
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            viewModel.companies.keys.toList()
        )
        // You can set the completionThreshold to 1 in the layout to start
        // searching once the user enters 1 character
        companiesTv.setAdapter(adapter)
        companiesTv.threshold = 1
        companiesTv.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        // When the user clicks a continent
        companiesTv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            val selectedCompany = adapterView?.getItemAtPosition(position) as String
            Toast.makeText(requireContext(), "You have select $selectedCompany", Toast.LENGTH_SHORT).show()
        }
    }
}