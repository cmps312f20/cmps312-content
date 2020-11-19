package qu.cmps312.countryvisit.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import qu.cmps312.countryvisit.R
import qu.cmps312.countryvisit.model.Visit
import kotlinx.android.synthetic.main.fragment_visits.*

class VisitsFragment : Fragment(R.layout.fragment_visits) {
    lateinit var visitAdapter: VisitAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addVisitFab.setOnClickListener {
            findNavController().navigate(R.id.toVisitEditorFragment)
        }

    }

    // ToDo: implement onVisitDeleted
    private fun onVisitDeleted(visit: Visit) {

    }

    // ToDo: implement onVisitUpdated
    private fun onVisitUpdated(visit: Visit) {
        findNavController().navigate(R.id.toVisitEditorFragment)
    }
}