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
import com.example.coroutinebasics.R
import kotlinx.android.synthetic.main.fragment_parallel_coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel
import kotlin.system.measureTimeMillis

class ParallelCoroutinesFragment : Fragment(R.layout.fragment_parallel_coroutines) {
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCompanySpinner()
        initCompaniesAutoCompleteTv()

        //companiesTv.text = "Apple, Tesla, Microsoft"

        var score = 0
        incrementBtn.setOnClickListener {
            score++
            scoreTv.text = score.toString()
        }

        getStockPriceBtn.setOnClickListener {
            priceTv.text = "In progress..."
            lifecycleScope.launch {
                val price = viewModel.getStockPrice(companySp.selectedItem.toString())
                priceTv.text = price.toString()
            }
        }

        getStockPricesBtn.setOnClickListener {
            resultsTv.text = ""
            executionTimeTv.text = "In progress..."
            val companies = companiesTv.text.toString().trim().removeTrailingComma().split(",")

            lifecycleScope.launch {
                val time = measureTimeMillis {
                //val prices =
                    if (parallelSwitch.isChecked)
                        viewModel.processInParallel(companies).collect {
                            resultsTv.text = "${resultsTv.text} \n$it"
                        }
                    else
                        viewModel.processSequentially(companies).collect {
                            resultsTv.text = "${resultsTv.text} \n$it"
                        }

                    //resultsTv.text = toString(companies, prices)
                }
                executionTimeTv.text = time.toString()
            }
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