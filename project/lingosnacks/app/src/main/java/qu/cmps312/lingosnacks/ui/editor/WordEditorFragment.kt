package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.word_editor_fragment.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Word
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel
import java.text.SimpleDateFormat
import java.util.*


class WordEditorFragment : Fragment(R.layout.word_editor_fragment) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val words = packageEditorViewModel.learningPackage.words
        val wordEditorAdapter = WordEditorAdapter(words, ::openDefinitionEditor, ::openSentenceEditor)

        wordsRv.apply {
            adapter = wordEditorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addBtn.setOnClickListener {
            words.add(Word(""))
            wordEditorAdapter.notifyItemInserted(wordEditorAdapter.itemCount)
            println(">> Debug: add a new word! ${wordEditorAdapter.itemCount}")
        }

        if (words.isEmpty())
            addBtn.performClick()

        saveBtn.setOnClickListener {
            packageEditorViewModel.learningPackage.apply {
                this.author = authViewModel.getCurrentUserInfo().email
                this.keywords = this.words.joinToString(", ") { it.text }
                this.lastUpdatedDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa").format(Date())
                this.version++
            }
            packageViewModel.upsertPackage(packageEditorViewModel.learningPackage)
            findNavController().navigate(R.id.toHomeFragment)
        }
    }

    private fun openDefinitionEditor(selectedWordIndex: Int) {
        packageEditorViewModel.selectedWordIndex = selectedWordIndex
        findNavController().navigate(R.id.toDefinitionEditor)
    }

    private fun openSentenceEditor(selectedWordIndex: Int) {
        packageEditorViewModel.selectedWordIndex = selectedWordIndex
        findNavController().navigate(R.id.toSentenceEditor)
    }
}