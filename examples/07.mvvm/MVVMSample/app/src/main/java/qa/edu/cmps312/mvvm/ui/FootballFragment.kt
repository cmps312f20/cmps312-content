package qa.edu.cmps312.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import qa.edu.cmps312.mvvm.viewmodel.FootballViewModel
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.databinding.FragmentFootballBinding

class FootballFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the view layout and obtain an instance of the binding class.
        val binding: FragmentFootballBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_football, container, false)

        // Specify the current activity as the lifecycle owner of the binding instance
        binding.lifecycleOwner = this

        // Associate the Activity with the ViewModel
        //val viewModel by viewModels<MainActivityViewModel>()
        //Or ViewModelProvider(<this activity>).get(<Your ViewModel>.class)
        //val viewModel: MainActivityViewModel by activityViewModels()
        // viewModel scoped with fragment
        //val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        //
        val viewModel = ViewModelProvider(requireActivity()).get(FootballViewModel::class.java)

        // Bind layout with ViewModel
        // Binding to LiveData triggers UI updates when the data changes
        binding.viewModel = viewModel

        binding.closeAppBtn.setOnClickListener {
            // Finish the activity - activity will be destroyed and the app will close
            requireActivity().finish()
        }

        //team1ScoreTv.text = viewModel.team1Score.toString()
        binding.team1IncrementBtn.setOnClickListener {
            viewModel.incrementTeam1Score()
            //team1ScoreTv.text = viewModel.team1Score.toString()
        }

        //team2ScoreTv.text = viewModel.team2Score.toString()
        binding.team2IncrementBtn.setOnClickListener {
            viewModel.incrementTeam2Score()
            //team2ScoreTv.text = viewModel.team2Score.toString()
        }

        viewModel.team1Score.observe(requireActivity()) {
            Log.d("FF.team1Score.observe", it.toString())
            //team1ScoreTv.text = it.toString()
        }

        // Observe team2Score LiveData object exposed by the ViewModel
        // passing this as parameter allows LiveData to remove this subscription when the activity is destroyed
        viewModel.team2Score.observe(requireActivity()) {
            //team2ScoreTv.text = it.toString()
            Log.d("FF.team2Score.observe", it.toString())
        }

        viewModel.currentWeatherFlow.observe(requireActivity()) {
            //binding.weatherTv.text = it
            Log.d("FF.weatherFlow.observe", it)
        }

        viewModel.timeRemainingFlow.observe(requireActivity()) {
            Log.d("FF.timeRemainingFlow", it)
        }
        return binding.root
        /*
        team1ScoreTv.text = team1Score.toString()
        team1IncrementBtn.setOnClickListener { 
            team1Score++
            team1ScoreTv.text = team1Score.toString()
        }

        team2ScoreTv.text = team2Score.toString()
        team2IncrementBtn.setOnClickListener {
            team2Score++
            team2ScoreTv.text = team2Score.toString()
        }
      */
    }

/*    override fun onConfigurationChanged(newConfig: Configuration) {
        val language = getDefault().language
        val orientation = when(newConfig.orientation) {
             Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
             else -> "Portrait"
        }

        Log.d(
            TAG,
            "*** onConfigurationChanged. Language = $language - Orientation = $orientation ***"
        )
        super.onConfigurationChanged(newConfig)
    }*/
}