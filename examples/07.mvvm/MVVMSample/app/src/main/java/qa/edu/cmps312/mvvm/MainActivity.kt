package qa.edu.cmps312.mvvm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Associate the Activity with the ViewModel
        val viewModel by viewModels<MainActivityViewModel>()
        //Or ViewModelProvider(<this activity>).get(<Your ViewModel>.class)
        //val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        team1ScoreTv.text = viewModel.team1Score.toString()
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
            team1ScoreTv.text = it.toString()
        })

        viewModel.team2Score.observe(this, {
            team2ScoreTv.text = it.toString()
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
}