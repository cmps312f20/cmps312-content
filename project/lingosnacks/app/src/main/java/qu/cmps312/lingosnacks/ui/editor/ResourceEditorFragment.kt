package qu.cmps312.lingosnacks.ui.editor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.resource_editor_fragment.*
import kotlinx.android.synthetic.main.resource_editor_fragment.addBtn
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Resource
import qu.cmps312.lingosnacks.model.ResourceTypeEnum
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel


class ResourceEditorFragment : Fragment(R.layout.resource_editor_fragment) {
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resources = packageEditorViewModel.getResources()
        val resourceEditorAdapter = ResourceEditorAdapter(resources)

        resourcesRv.apply {
            adapter = resourceEditorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addBtn.setOnClickListener {
            resources.add(Resource("", "", ResourceTypeEnum.Photo))
            resourceEditorAdapter.notifyItemInserted(resourceEditorAdapter.itemCount)
        }

        if (resources.isEmpty())
            addBtn.performClick()

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}