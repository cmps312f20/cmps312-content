package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.sentence_editor_fragment.*
import kotlinx.android.synthetic.main.sentence_editor_fragment.addBtn
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Sentence
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel


class SentenceEditorFragment : Fragment(R.layout.sentence_editor_fragment) {
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sentences = packageEditorViewModel.getSentences()
        val sentenceEditorAdapter = SentenceEditorAdapter(sentences, ::openResourceEditor)

        sentencesRv.apply {
            adapter = sentenceEditorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addBtn.setOnClickListener {
            sentences.add(Sentence(""))
            sentenceEditorAdapter.notifyItemInserted(sentenceEditorAdapter.itemCount)
        }

        if (sentences.isEmpty())
            addBtn.performClick()

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun openResourceEditor(selectedSentenceIndex: Int) {
        packageEditorViewModel.selectedSentenceIndex = selectedSentenceIndex
        findNavController().navigate(R.id.toResourceEditor)
    }
}