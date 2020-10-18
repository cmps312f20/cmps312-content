package qu.cmps312.coroutinebasics.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.coroutinebasics.R
import kotlinx.android.synthetic.main.fragment_cancel_coroutine.*
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel

class CancelCoroutineFragment : Fragment(R.layout.fragment_cancel_coroutine) {
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.nextValue.observe(requireActivity()) {
            fibonacciTv.text = it.toString()
        }

        fibonacciBtn.setOnClickListener {
            viewModel.startFibonacci()
        }

        cancelFibonacciBtn.setOnClickListener {
            viewModel.stopFibonacci()
        }
    }
}