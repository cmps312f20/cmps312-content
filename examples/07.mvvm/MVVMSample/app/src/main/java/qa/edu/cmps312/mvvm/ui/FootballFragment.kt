package qa.edu.cmps312.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.databinding.FragmentFootballBinding
import qa.edu.cmps312.mvvm.viewmodel.FootballViewModel

class FootballFragment : Fragment(R.layout.fragment_football) {
    private val TAG = "App.FootballFragment.Observe->"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain an instance of the view binding class.
        val viewBinding = FragmentFootballBinding.bind(view)
        // Specify the current fragment as the lifecycle owner
        viewBinding.lifecycleOwner = this

        // Obtain a ViewModel instance scoped to activity
        val viewModel by activityViewModels<FootballViewModel>() // Scoped to activity
        //val viewModel by viewModels<FootballViewModel>() // Scoped to fragment

        // Bind the View with the ViewModel
        // Binding to LiveData triggers UI updates when the data changes
        viewBinding.viewModel = viewModel

        viewBinding.closeAppBtn.setOnClickListener {
            // Finish the activity - activity will be destroyed and the app will close
            // We usually do this in an app. This is just for the demo
            requireActivity().finish()
        }

        viewBinding.team1IncrementBtn.setOnClickListener {
            viewModel.incrementTeam1Score()
        }

        viewBinding.team2IncrementBtn.setOnClickListener {
            viewModel.incrementTeam2Score()
        }

        viewModel.team1Score.observe(requireActivity()) {
            //team1ScoreTv.text = it.toString()
            Log.d(TAG, it.toString())
        }

        // Observe team1Score LiveData object exposed by the ViewModel
        // passing the activity as a parameter allows LiveData to remove this subscription when the activity is destroyed
        viewModel.team1Score.observe(requireActivity()) {
            Log.d("${TAG}team1Score", it.toString())
            //team1ScoreTv.text = it.toString()
        }

        viewModel.team2Score.observe(requireActivity()) {
            //team2ScoreTv.text = it.toString()
            Log.d("${TAG}team2Score", it.toString())
        }

        viewModel.newsFlow.observe(requireActivity()) {
            //binding.newsTv.text = it
            Log.d("${TAG}newsFlow", it)
        }

        viewModel.timeRemainingFlow.observe(requireActivity()) {
            Log.d("${TAG}timeRemainingFlow", it)
        }

        //Watch the fragment cycle events
        val lifeCycleObserver = ActivityFragmentObserver(lifecycle, "FootballFragment ⚽⚽")
    }
}