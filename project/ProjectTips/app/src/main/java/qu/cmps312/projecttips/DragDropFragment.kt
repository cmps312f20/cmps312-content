package qu.cmps312.projecttips

import android.content.ClipData
import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projecttips.R
import kotlinx.android.synthetic.main.fragment_drag_drop.*


class DragDropFragment : Fragment(R.layout.fragment_drag_drop) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onCreate(savedInstanceState)

        // Set the scrambled words
        word1Tv.text = "University"
        word2Tv.text = "Qatar"

        // Set the correct answer in the tag attribute of the target
        target1Tv.tag = "Qatar"
        target2Tv.tag = "University"

        // Allow dragging
        word1Tv.setOnTouchListener(::touchListener)
        word2Tv.setOnTouchListener(::touchListener)

        // Lister to drop event
        target1Tv.setOnDragListener(dragListen)
        target2Tv.setOnDragListener(dragListen)
    }

    private fun touchListener(view: View, event: MotionEvent) : Boolean {
        // Data to be dragged and dropped on the target-drop views
        val data = ClipData.newPlainText("text", (view as TextView).text)
        // Show to be shown while tragging the textview
        val shadowBuilder = View.DragShadowBuilder(view) //DragShadowBuilder(v)
        view.startDragAndDrop(data, shadowBuilder, view, 0)
        return true
    }

    private val dragListen = View.OnDragListener { view, event ->
        val receiverView = view as TextView

        when (event.action) {
            // the drag point has entered the bounding box of the View
            DragEvent.ACTION_DRAG_ENTERED -> {
                receiverView.setBackgroundColor(Color.YELLOW)
                true
            }
            // the user has moved the drag shadow outside the bounding box of the View
            DragEvent.ACTION_DRAG_EXITED -> {
                receiverView.setBackgroundResource(android.R.color.holo_green_light)
                // Force textview update
                receiverView.invalidate()
                true
            }

            //drag shadow has been released, the drag point is within the bounding box of the View
            DragEvent.ACTION_DROP -> {
                // If the tag on target = dragged text then accept the drop
                val dragData = event.clipData.getItemAt(0).text
                if (receiverView.tag == dragData) {
                    receiverView.setBackgroundColor(Color.WHITE)

                    receiverView.text = "$dragData"
                    // Make the view that was dragged and dropped invisible
                    val view = event.localState as View
                    view.visibility = View.INVISIBLE
                    // Khalas do not accept drop event
                    receiverView.setOnDragListener(null)
                    true
                } else {
                    false
                }
            }

            else -> true // Do nothing
        }
    }
}