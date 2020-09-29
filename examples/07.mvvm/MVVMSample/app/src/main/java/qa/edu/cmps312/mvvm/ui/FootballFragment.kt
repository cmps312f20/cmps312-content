package qa.edu.cmps312.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.databinding.FragmentFootballBinding
import qa.edu.cmps312.mvvm.viewmodel.FootballViewModel

class FootballFragment : Fragment() {
    private val TAG = "App.FootballFragment.Observe->"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the view layout and obtain an instance of the binding class
        val binding: FragmentFootballBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_football, container, false)

        // Specify the current fragment as the lifecycle owner of the binding instance
        binding.lifecycleOwner = this

        // Associate the Activity with the ViewModel
        val viewModel by activityViewModels<FootballViewModel>() // Scoped to activity
        //val viewModel by viewModels<FootballViewModel>() // Scoped to fragment
        //Or ViewModelProvider(<this activity>).get(<Your ViewModel>.class)
        // viewModel scoped to fragment
        //val viewModel = ViewModelProvider(this).get(FootballViewModel::class.java)

        // Bind layout with ViewModel
        // Binding to LiveData triggers UI updates when the data changes
        binding.viewModel = viewModel

        binding.closeAppBtn.setOnClickListener {
            // Finish the activity - activity will be destroyed and the app will close
            requireActivity().finish()
        }

        binding.team1IncrementBtn.setOnClickListener {
            viewModel.incrementTeam1Score()
        }

        binding.team2IncrementBtn.setOnClickListener {
            viewModel.incrementTeam2Score()
        }

        viewModel.team1Score.observe(requireActivity()) {
            Log.d("${TAG}team1Score", it.toString())
            //team1ScoreTv.text = it.toString()
        }

        // Observe team2Score LiveData object exposed by the ViewModel
        // passing the activity as a parameter allows LiveData to remove this subscription when the activity is destroyed
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
        return binding.root
    }
}