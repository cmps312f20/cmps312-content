package qu.cmps312.lingosnacks.ui.games

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leader_board.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel

class LeaderBoardFragment : Fragment(R.layout.fragment_leader_board) {
    private val packageViewModel by activityViewModels<PackageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leaderBoardRv.apply {
            adapter= LeaderBoardAdapter(packageViewModel.getLeaderBoard())
            layoutManager = LinearLayoutManager(context)
        }
    }
}