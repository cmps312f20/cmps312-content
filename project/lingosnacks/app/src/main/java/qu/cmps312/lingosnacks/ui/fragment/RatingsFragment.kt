package qu.cmps312.lingosnacks.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_ratings.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.adapter.RatingAdapter
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel


class RatingsFragment : Fragment(R.layout.fragment_ratings) {
    private val packageViewModel by activityViewModels<PackageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRatings.apply {
            adapter= RatingAdapter(packageViewModel.getRatings())
            layoutManager = LinearLayoutManager(context)
        }
    }
}