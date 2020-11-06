package qu.cmps312.lingosnacks.ui.games

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_match_definition.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Score
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel
import java.text.SimpleDateFormat
import java.util.*

class WordDefinition(val word:String, val definition: String)
class MatchDefinitionFragment : Fragment(R.layout.fragment_match_definition) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    private var currentIndex = 0
    private var score = 0
    private var outOf = 0
    private var definitions = listOf<WordDefinition>()
    private val previousTextViews = mutableListOf<TextView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedPackage = packageViewModel.selectedPackage!!
        definitions = selectedPackage.words.flatMap { word ->
            word.definitions.map { WordDefinition(word.text, it.text) }
        }

        displayDefinition(currentIndex)
        nextBtn.setOnClickListener { onMoveNext(it, 1) }
        prevBtn.setOnClickListener { onMoveNext(it, -1) }
    }

    private fun onMoveNext(view: View, step: Int) {
        currentIndex += step
        currentIndex = (currentIndex + definitions.count()) % definitions.count()
        previousTextViews.forEach { mainLayout.removeView(it) }
        previousTextViews.clear()
        displayDefinition(currentIndex)
    }

   private fun displayDefinition(index: Int) {
        currentIndexTv.text = "${index + 1} of ${definitions.size}"

        val currentDefinition = definitions[index]
        var shuffledDefs = definitions.filter { it.word !=currentDefinition.word }.distinctBy { it.word }.shuffled().take(5)
        shuffledDefs = (shuffledDefs + currentDefinition).shuffled()

       definitionTv.text = currentDefinition.definition
       shuffledDefs.forEach { definition ->
           val tag = if (definition.definition == currentDefinition.definition) definition.word else ""
            val tv = createTextView(definition.word, tag)
            previousTextViews.add(tv)
            tv.setOnClickListener {
                onWordClicked(it)
            }
            mainLayout.addView(tv)
            wordsFlow.addView(tv)
       }
    }

    private fun onWordClicked(view: View) {
        val clickedAnswer = view as TextView
        val answerCorrect = (clickedAnswer.text == clickedAnswer.tag)
        outOf++
        if (answerCorrect) {
            clickedAnswer.setBackgroundResource(android.R.color.holo_orange_light)
            Toast.makeText(requireContext(), "Well Done!", Toast.LENGTH_SHORT).show()
            // Do not respond to clicks after correct answer
            previousTextViews.forEach { it.setOnClickListener(null) }
            score++
        }
        else {
            clickedAnswer.setBackgroundColor(Color.RED)
            clickedAnswer.setOnClickListener(null)
            Toast.makeText(
                requireContext(),
                "Try again!",
                Toast.LENGTH_SHORT
            ).show()
            if (score >= 1) score--
        }
        scoreView.text = "Score: $score / $outOf"
    }

    private fun createTextView(text: String, tag: String = "") = run {
        val tv = TextView(requireContext())
        tv.apply {
            this.text = text
            this.tag = tag
            textSize = 21F
            typeface = Typeface.DEFAULT_BOLD
            setPadding(25, 25, 25, 25)
            setBackgroundResource(android.R.color.holo_blue_light)
            id = View.generateViewId()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val uid = authViewModel.getCurrentUserInfo().uid
        if (uid.isNotEmpty() && outOf > 0) {
            val scoreId = ""
            val gameName = "Match Definition"
            val doneOn = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa").format(Date())
            val newScore = Score(scoreId,uid, gameName,
            score, outOf, doneOn)

            println(">> Debug: UnscrambleSentenceFragment.onDestroy: $newScore")
            packageViewModel.addScore(newScore)
        }
    }
}









