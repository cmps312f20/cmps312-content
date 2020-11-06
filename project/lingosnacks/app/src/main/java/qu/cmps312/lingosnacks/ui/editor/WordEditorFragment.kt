package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_ratings.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel


class WordEditorFragment : Fragment(R.layout.fragment_word_editor) {
    private val packageViewModel by activityViewModels<PackageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordsRv.apply {
            adapter= WordAdapter(packageViewModel.selectedPackage?.words!!)
            layoutManager = LinearLayoutManager(context)
        }
    }
}