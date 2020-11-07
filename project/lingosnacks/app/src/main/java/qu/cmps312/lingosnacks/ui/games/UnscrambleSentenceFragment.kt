package qu.cmps312.lingosnacks.ui.games

import android.content.ClipData
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_unscramble_sentence.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Score
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel
import java.text.SimpleDateFormat
import java.util.*

class UnscrambleSentenceFragment : Fragment(R.layout.fragment_unscramble_sentence) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    private var currentIndex = 0
    private var score = 0
    private var outOf = 0
    private var sentences = listOf<String>()
    private val previousTextViews = mutableListOf<TextView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedPackage = packageViewModel.selectedPackage!!
        sentences = selectedPackage.words.flatMap { word ->
            word.sentences.map { it.text }
        }

        if (sentences.isEmpty()) return

        displaySentence(currentIndex)
        nextBtn.setOnClickListener { onMoveNext(it, 1) }
        prevBtn.setOnClickListener { onMoveNext(it, -1) }
    }

    private fun onMoveNext(view: View, step: Int) {
        currentIndex += step
        currentIndex = (currentIndex + sentences.count()) % sentences.count()
        previousTextViews.forEach { mainLayout.removeView(it) }
        previousTextViews.clear()
        displaySentence(currentIndex)
    }

   private fun displaySentence(index: Int) {
        currentIndexTv.text = "${index + 1} of ${sentences.size}"

        val sentence = sentences[index]
        var words =sentence.split(" ").shuffled()

        words.forEach {
            val tv= createTextView(it)
            previousTextViews.add(tv)
            tv.setOnTouchListener(::touchListener)
            mainLayout.addView(tv)
            definitionTv.addView(tv)
        }
        words = sentence.split(" ")
        words.forEachIndexed { i, word ->
            val tv = createTextView((i + 1).toString(), word)
            previousTextViews.add(tv)
            tv.setOnDragListener(dragListen)
            mainLayout.addView(tv)
            wordsFlow.addView(tv)
        }
    }

    private fun touchListener(view: View, event: MotionEvent): Boolean {
        // Data to be dragged and dropped on the target-drop views
        val data = ClipData.newPlainText("text", (view as TextView).text)
        // Show to be shown while tragging the textview
        val shadowBuilder = View.DragShadowBuilder(view) //DragShadowBuilder(v)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            view.startDragAndDrop(data, shadowBuilder, view, 0)
        }
        return true
    }

    private val dragListen = View.OnDragListener { view, event ->
        val answerView = view as TextView

        when (event.action) {
            //if the word is in the drag place it will show gray
            DragEvent.ACTION_DRAG_ENTERED -> {
                answerView.setBackgroundColor(Color.GRAY)
                true
            }
            // the user has moved the drag shadow outside the bounding box of the View
            DragEvent.ACTION_DRAG_EXITED -> {
                answerView.setBackgroundResource(android.R.color.white)

                answerView.invalidate()
                true
            }

            DragEvent.ACTION_DROP -> {
                val dragData = event.clipData.getItemAt(0).text
                outOf++
                //to check if the user put the word in the correct place - background changes
                if (answerView.tag == dragData) {
                    answerView.setBackgroundColor(Color.parseColor("#618FE0F4"))
                    Toast.makeText(requireContext(), "Well Done!", Toast.LENGTH_SHORT).show()
                    answerView.text = dragData
                    score++
                    scoreView.text = "Score: $score / $outOf"

                    //after its put in the right position in the sentence then the word will be removed from the list
                    // and will be added in the right position of the sentence
                    val view = event.localState as View
                    view.visibility = View.INVISIBLE
                    answerView.setOnDragListener(null)

                    true
                } else {
                    //the drag box will turn red if it wasn't in the right place
                    answerView.setBackgroundColor(Color.RED)
                    Toast.makeText(
                        requireContext(),
                        "Try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                    //if (score >= 1) score--
                    scoreView.text = "Score: $score / $outOf"
                    false
                }
            }
            else -> true
        }
    }

    private fun createTextView(text: String, tag: String = "") = run {
        val tv = TextView(requireContext())
        tv.apply {
            this.text = text
            this.tag = tag
            textSize = 21F
            typeface = Typeface.DEFAULT_BOLD
            setPadding(25, 25, 25, 25)
            if (tag != "")
                setBackgroundResource(android.R.color.holo_blue_light)
            else
                setBackgroundResource(android.R.color.holo_orange_light)
            id = View.generateViewId()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val uid = authViewModel.getCurrentUserInfo().uid
        if (uid.isNotEmpty() && outOf > 0) {
            val scoreId = ""
            val gameName = "Unscramble Sentence"
            val doneOn = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa").format(Date())
            val newScore = Score(scoreId,uid, gameName,
            score, outOf, doneOn)

            println(">> Debug: UnscrambleSentenceFragment.onDestroy: $newScore")
            packageViewModel.addScore(newScore)
        }
    }
}









