package qu.cmps312.lingosnacks.ui.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_editor_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Word

class WordAdapter(private val words: List<Word>) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.wordEt.setText(word.text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.word_editor_item, parent, false)
    )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(words[position])

    override fun getItemCount() = words.size
}