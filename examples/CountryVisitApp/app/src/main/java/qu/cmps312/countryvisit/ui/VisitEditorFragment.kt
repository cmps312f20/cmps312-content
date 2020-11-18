package qu.cmps312.countryvisit.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_visit_editor.*
import qu.cmps312.countryvisit.R

class VisitEditorFragment : Fragment(R.layout.fragment_visit_editor) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveBtn.setOnClickListener {
        }

    }
}