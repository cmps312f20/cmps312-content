package qa.edu.cmps312.mvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import qa.edu.cmps312.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        //setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        // Connect the binding in the Activity with the  ViewModel
        val viewModel by viewModels<MainActivityViewModel>()
        //Or ViewModelProvider(<this activity>).get(<Your ViewModel>.class)
        //val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        // Assign data binding with the viewModel
        // Binding to LiveData triggers UI updates when the data changes
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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


        viewModel.team1Score.observe(this, {
            Log.d("team1Score.observe", it.toString())
            //team1ScoreTv.text = it.toString()
        })

        viewModel.team2Score.observe(this, {
            //team2ScoreTv.text = it.toString()
            Log.d("team2Score.observe", it.toString())
        })

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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
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