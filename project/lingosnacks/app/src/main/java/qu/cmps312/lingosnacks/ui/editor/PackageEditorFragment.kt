package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_package_editor.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel

fun Spinner.setValue (value: String) {
    var spinnerAdapter = this.adapter as ArrayAdapter<String>
    this.setSelection(spinnerAdapter.getPosition(value))
}

class PackageEditorFragment : Fragment(R.layout.fragment_package_editor) {
    private val packageViewModel by activityViewModels<PackageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        packageViewModel.selectedPackage?.let {
            titleEt.setText(it.title)
            descriptionEt.setText(it.description)
            categorySp.setValue(it.category)
            languageSp.setValue(it.language)
            levelSp.setValue(it.level)

            iconUrlEt.setText(it.iconUrl)
            //= it.keywords: MutableList<String>,
        }
    }
}