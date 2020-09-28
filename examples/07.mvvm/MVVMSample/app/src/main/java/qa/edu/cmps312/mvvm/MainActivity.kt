package qa.edu.cmps312.mvvm

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import qa.edu.cmps312.mvvm.databinding.ActivityMainBinding
import java.util.*
import java.util.Locale.getDefault

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get current language and screen orientation
        val language = getDefault().language
        val orientation = when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            else -> "Portrait"
        }

        Log.d(TAG,
            "*** MainActivity.onCreate. Language = $language - Orientation = $orientation ***"
        )

        //setContentView(R.layout.activity_main)
        // Inflate the view layout and obtain an instance of the binding class.
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        // Specify the current activity as the lifecycle owner of the binding instance
        binding.lifecycleOwner = this

        // Associate the Activity with the ViewModel
        val viewModel by viewModels<MainActivityViewModel>()
        //Or ViewModelProvider(<this activity>).get(<Your ViewModel>.class)
        //val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        // Connect the binding in the Activity with the  ViewModel
        // Binding to LiveData triggers UI updates when the data changes
        binding.viewModel = viewModel

        closeAppBtn.setOnClickListener {
            // Finish the activity - activity will be destroyed and the app will close
            finish()
        }

        //team1ScoreTv.text = viewModel.team1Score.toString()
        team1IncrementBtn.setOnClickListener {
            viewModel.incrementTeam1Score()
            //team1ScoreTv.text = viewModel.team1Score.toString()
        }

        //team2ScoreTv.text = viewModel.team2Score.toString()
        team2IncrementBtn.setOnClickListener {
            viewModel.incrementTeam2Score()
            //team2ScoreTv.text = viewModel.team2Score.toString()
        }


        viewModel.team1Score.observe(this) {
            Log.d("$TAG.team1Score.observe", it.toString())
            //team1ScoreTv.text = it.toString()
        }

        // Observe team2Score LiveData object exposed by the ViewModel
        // passing this as parameter allows LiveData to remove this subscription when the activity is destroyed
        viewModel.team2Score.observe(this) {
            //team2ScoreTv.text = it.toString()
            Log.d("$TAG.team2Score.observe", it.toString())
        }

        viewModel.currentWeatherFlow.observe(this) {
            //binding.weatherTv.text = it
            Log.d("$TAG.weatherFlow.observe", it)
        }

        viewModel.timeRemainingFlow.observe(this) {
            Log.d("$TAG.timeRemainingFlow", it)
        }
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
        Log.d(TAG, "onDestroy. Is Finishing: $isFinishing")
    }
}