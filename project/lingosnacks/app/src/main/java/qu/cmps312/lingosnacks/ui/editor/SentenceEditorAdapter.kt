package qu.cmps312.lingosnacks.ui.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sentence_editor_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Sentence

class SentenceEditorAdapter(val sentences: MutableList<Sentence>,
           val onResourcesClickListener: (selectedSentenceIndex: Int) -> Unit,) :
    RecyclerView.Adapter<SentenceEditorAdapter.SentenceViewHolder>() {

    inner class SentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sentence: Sentence) {
            itemView.sentenceEt.setText(sentence.text)

            itemView.sentenceEt.doOnTextChanged { text, start, before, count ->
                sentences[adapterPosition].text = text.toString()
            }

            itemView.resourcesBtn.setOnClickListener {
                onResourcesClickListener(adapterPosition)
            }

            itemView.deleteBtn.setOnClickListener {
                val position = adapterPosition
                sentences.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SentenceViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.sentence_editor_item, parent, false)
    )

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) =
        holder.bind(sentences[position])

    override fun getItemCount() = sentences.size
}