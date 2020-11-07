package qu.cmps312.lingosnacks.ui.editor

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.resource_editor_fragment.*
import qu.cmps312.lingosnacks.BuildConfig
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Resource
import qu.cmps312.lingosnacks.model.ResourceTypeEnum
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel
import java.io.File
import java.util.*

/* Must add the following dependencies to build.gradle
   // Kotlin extensions - activity-ktx & fragment-ktx
    def activity_version = "1.2.0-beta01"
    implementation "androidx.activity:activity-ktx:${activity_version}"
    def fragment_version = "1.3.0-beta01"
    implementation "androidx.fragment:fragment-ktx:$fragment_version"
*/
/*
To create a file on Android:
In Manifest you need to add <provider> ... </provider>. See Manifest of this project
Also, need to add file_paths.xml user res/xml (see example in this project)
More info @ https://developer.android.com/reference/androidx/core/content/FileProvider
Interesting discussion @ https://stackoverflow.com/questions/21931169/fileprovider-throws-exception-on-geturiforfile/48103567#48103567
https://stackoverflow.com/questions/42516126/fileprovider-illegalargumentexception-failed-to-find-configured-root
I do not understand it fully but ok for now.
Bit more complicated than expected 😱 Android people what is! Why complicating it 😲?
*/

class ResourceEditorFragment : Fragment(R.layout.resource_editor_fragment) {
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()
    private var resource = Resource("", "", ResourceTypeEnum.Photo)
    private lateinit var photoUri : Uri
    private lateinit var resources : MutableList<Resource>
    private lateinit var resourceEditorAdapter :  ResourceEditorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resources = packageEditorViewModel.getResources()
        resourceEditorAdapter = ResourceEditorAdapter(resources)

        resourcesRv.apply {
            adapter = resourceEditorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addBtn.setOnClickListener {
            resource = Resource("", "", ResourceTypeEnum.Photo)
            resources.add(resource)
            resourceEditorAdapter.notifyItemInserted(resourceEditorAdapter.itemCount)
        }

        if (resources.isEmpty())
            addBtn.performClick()

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        selectMediaBtn.setOnClickListener {
            mediaPicker.launch("*/*")
        }

        takePhotoBtn.setOnClickListener {
            try {
                val photoFile = createPhotoFile()
                val authority = BuildConfig.APPLICATION_ID + ".fileProvider"
                photoUri = FileProvider.getUriForFile(requireContext(), authority, photoFile);
                takePicture.launch(photoUri)
            } catch (e: Exception) {
                println("Debug: $e")
            }
        }
    }

    private val mediaPicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Toast.makeText(requireContext(), "Image Uri: $uri", Toast.LENGTH_LONG).show()
            println("Debug: $uri")
            uri?.let {
                // Maybe there is a better way to distinguish photo from video
                val resourceType = if (it.toString().contains("image"))
                    enumValueOf<ResourceTypeEnum>("Photo")
                else
                    enumValueOf<ResourceTypeEnum>("Video")

                resource = Resource("", it.toString(), resourceType)
                resources.add(resource)
                resourceEditorAdapter.notifyItemInserted(resourceEditorAdapter.itemCount)
            }
        }

     private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            println("Debug: photoUri = $photoUri")
            resource = Resource("", photoUri.toString(), ResourceTypeEnum.Photo)
            resources.add(resource)
            resourceEditorAdapter.notifyItemInserted(resourceEditorAdapter.itemCount)
        }
    }

    private fun createPhotoFile(): File {
        val photoFileName = UUID.randomUUID().toString()
        // Get app files path
        val path = requireContext().filesDir
        println("Debug: requireContext().filesDir = $path")
        // Create images folder if does not exist
        val imgPath = File(path, "images")
        imgPath.mkdirs()

        // Create image file and return it
        return File(imgPath, "$photoFileName.jpg")
    }
}