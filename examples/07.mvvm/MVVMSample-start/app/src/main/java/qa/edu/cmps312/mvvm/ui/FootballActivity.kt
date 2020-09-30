package qa.edu.cmps312.mvvm.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.activity_football.*
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.viewmodel.FootballViewModel
import java.util.*

class FootballActivity : AppCompatActivity() {
    //private lateinit var navController: NavController
    private val TAG = "LifeCycle->Football \uD83D\uDE88\uD83D\uDE88"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football)

        val viewModel by viewModels<FootballViewModel>()

        team1ScoreTv.text = viewModel.team1Score.toString()
        team1IncrementBtn.setOnClickListener {
            viewModel.incrementTeam1Score()
            team1ScoreTv.text = viewModel.team1Score.toString()
        }

        team2ScoreTv.text = viewModel.team2Score.toString()
        team2IncrementBtn.setOnClickListener {
            viewModel.incrementTeam2Score()
            team2ScoreTv.text = viewModel.team2Score.toString()
        }










        /*
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


         */
        //Watch the fragment cycle events
        //val lifeCycleObserver = ActivityFragmentObserver(lifecycle, "FootballFragment ⚽⚽")
        //return binding.root

        // Get current language and screen orientation
        val language = Locale.getDefault().language
        val orientation = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            else -> "Portrait"
        }

        Log.d(
            TAG,
            "onCreate. Language = $language - Orientation = $orientation"
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"--- onPause ---")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "☠️☠️ onDestroy ☠️☠️. Is Finishing: $isFinishing")
    }
}