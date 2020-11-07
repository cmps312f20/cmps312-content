package qu.cmps312.lingosnacks.ui.editor

import android.content.Context
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.android.synthetic.main.word_editor_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Word

class WordEditorAdapter(val words: MutableList<Word>,
    val onDefinitionsClickListener: (selectedWordIndex: Int) -> Unit,
    val onSentencesClickListener: (selectedWordIndex: Int) -> Unit) :
    RecyclerView.Adapter<WordEditorAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.wordEt.setText(word.text)
            itemView.definitionsTv.text = "D(${word.definitions.size})"
            itemView.sentencesTv.text = "S(${word.sentences.size})"
            itemView.wordEt.doOnTextChanged { text, start, before, count ->
                words[adapterPosition].text = text.toString()
            }


            itemView.deleteBtn.setOnClickListener {
                val position = adapterPosition
                words.removeAt(position)
                notifyItemRemoved(position)
            }

            itemView.definitionsTv.setOnClickListener {
                onDefinitionsClickListener(adapterPosition)
            }

            itemView.sentencesTv.setOnClickListener {
                onSentencesClickListener(adapterPosition)
            }
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