package qu.cmps312.lingosnacks.ui.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.definition_editor_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Definition

class DefinitionEditorAdapter(val definitions: MutableList<Definition>) :
    RecyclerView.Adapter<DefinitionEditorAdapter.DefinitionViewHolder>() {

    inner class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(definition: Definition) {
            itemView.definitionEt.setText(definition.text)
            itemView.sourceEt.setText(definition.source)

            itemView.definitionEt.doOnTextChanged { text, start, before, count ->
                definitions[adapterPosition].text = text.toString()
            }

            itemView.sourceEt.doOnTextChanged { text, start, before, count ->
                definitions[adapterPosition].source = text.toString()
            }

            itemView.deleteBtn.setOnClickListener {
                val position = adapterPosition
                definitions.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefinitionViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.definition_editor_item, parent, false)
    )

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) =
        holder.bind(definitions[position])

    override fun getItemCount() = definitions.size
}