package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_ratings.definitionsRv
import kotlinx.android.synthetic.main.definition_editor_fragment.*
import kotlinx.android.synthetic.main.definition_editor_fragment.addBtn
import kotlinx.android.synthetic.main.word_editor_fragment.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Definition
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel


class DefinitionEditorFragment : Fragment(R.layout.definition_editor_fragment) {
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val definitions = packageEditorViewModel.getWordDefinitions()
        val definitionEditorAdapter = DefinitionEditorAdapter(definitions)

        definitionsRv.apply {
            adapter = definitionEditorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addBtn.setOnClickListener {
            definitions.add(Definition("", ""))
            definitionEditorAdapter.notifyItemInserted(definitionEditorAdapter.itemCount)
        }

        if (definitions.isEmpty())
            addBtn.performClick()

        submitBtn.setOnClickListener {
            findNavController().navigateUp()
            //packageEditorViewModel.setWordDefinitions(definitions)
            //println(">> Debug: new/updated definitions $definitions")
            //findNavController().navigateUp()
            //packageEditorViewModel.getSelectedWord().definitions.forEach {
                //println(">> Debug: ${it.text}")
                //packageEditorViewModel.setWordDefinitions(definitionEditorAdapter.definitions)
                // Sync with selected package
                //packageEditorViewModel.learningPackage.definitions = definitionEditorAdapter.definitions
            //}
        }
    }
}