package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_package_editor.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel

fun Spinner.setValue (value: String) {
    var spinnerAdapter = this.adapter as ArrayAdapter<String>
    val position = spinnerAdapter.getPosition(value)
    if (position >=0)
        this.setSelection(position)
}

class PackageEditorFragment : Fragment(R.layout.fragment_package_editor) {
    //private val packageViewModel by activityViewModels<PackageViewModel>()
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        packageEditorViewModel.learningPackage.apply {
            titleEt.setText(this.title)
            descriptionEt.setText(this.description)
            categorySp.setValue(this.category)
            languageSp.setValue(this.language)
            levelSp.setValue(this.level)
            iconUrlEt.setText(this.iconUrl)
        }

        nextBtn.setOnClickListener {
            packageEditorViewModel.learningPackage.apply {
                this.title = titleEt.text.toString()
                this.description = descriptionEt.text.toString()
                this.category = categorySp.selectedItem.toString()
                this.language = languageSp.selectedItem.toString()
                this.level = levelSp.selectedItem.toString()
                this.iconUrl = iconUrlEt.text.toString()
            }
            println(">> Debug: new/updated learningPackage: ${packageEditorViewModel.learningPackage}")
            findNavController().navigate(R.id.toWordEditor)
        }
    }
}