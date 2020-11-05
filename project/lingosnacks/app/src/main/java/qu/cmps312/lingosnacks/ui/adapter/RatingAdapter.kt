package qu.cmps312.lingosnacks.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rating_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Rating

class RatingAdapter(private val ratings: List<Rating>) :
    RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {

    inner class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rating: Rating) {
            itemView.apply {
                doneByTv.text = rating.doneBy
                doneOnTv.text = rating.doneOn
                commentTv.text = rating.comment
                ratingBar.rating = rating.rating.toFloat()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RatingViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.rating_item, parent, false)
    )

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) =
        holder.bind(ratings[position])

    override fun getItemCount() = ratings.size
}