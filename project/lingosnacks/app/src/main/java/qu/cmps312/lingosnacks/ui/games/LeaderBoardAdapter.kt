package qu.cmps312.lingosnacks.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.leader_board_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.repositories.LeaderBoardMember
import qu.cmps312.lingosnacks.repositories.round

class LeaderBoardAdapter(private val leaders: List<LeaderBoardMember>) :
    RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>() {

    inner class LeaderBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(leader: LeaderBoardMember) {
            val defaultImg = ContextCompat.getDrawable(itemView.context, R.drawable.ic_user)
            itemView.apply {
                rankTv.text = leader.rank.toString()
                userNameTv.text = leader.displayName
                gameNameTv.text = leader.gameName
                if (leader.photoUri.isEmpty()) {
                    photoIv.setImageDrawable(defaultImg)
                } else {
                    photoIv.load(leader.photoUri)
                }
                val scorePercentage = (leader.score.toDouble() / leader.outOf * 100.0).round(2)
                val score = "${leader.score} / ${leader.outOf} ($scorePercentage%)"
                scoreTv.text = score
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LeaderBoardViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.leader_board_item, parent, false)
    )

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) =
        holder.bind(leaders[position])

    override fun getItemCount() = leaders.size
}