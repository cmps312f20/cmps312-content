package qu.cmps312.projecttips

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projecttips.R
import kotlinx.android.synthetic.main.fragment_dynamic_textviews.*

class DynamicTextViewsFragment : Fragment(R.layout.fragment_dynamic_textviews) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sentence = "Qatar will host World Cup 2022"
        var words = sentence.split(" ").shuffled()
        words.forEach {
                val tv = createTextView(it)
                tv.setOnClickListener {
                   Toast.makeText(requireContext(), "text = ${(it as TextView).text}", Toast.LENGTH_SHORT).show()
                }
                mainLayout.addView(tv)
                scrambledFlow.addView(tv)
        }

        words = sentence.split(" ")
        words.forEachIndexed { index, word ->
            val tv = createTextView((index+1).toString(), word)
            tv.setOnClickListener {
                Toast.makeText(requireContext(), "Tag = ${it.tag}", Toast.LENGTH_SHORT).show()
            }
            mainLayout.addView(tv)
            originalFlow.addView(tv)
        }
    }

    private fun createTextView(text: String, tag: String = "") = run {
        val tv = TextView(requireContext())
        tv.apply {
            this.text = text
            this.tag = tag
            textSize = 24F
            typeface = Typeface.DEFAULT_BOLD
            setPadding(25, 25, 25, 25)
            if (tag.isNotEmpty())
                setBackgroundResource(android.R.color.holo_green_light)
            else
                setBackgroundResource(android.R.color.holo_blue_light)
            id = View.generateViewId()
        }
    }
}