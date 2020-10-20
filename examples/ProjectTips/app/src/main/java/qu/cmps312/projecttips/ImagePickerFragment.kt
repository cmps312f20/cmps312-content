package qu.cmps312.projecttips

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.projecttips.BuildConfig
import com.example.projecttips.R
import kotlinx.android.synthetic.main.fragment_image_picker.*
import java.io.File
import java.util.*

/* Must add the following dependencies to build.gradle
   // Kotlin extensions - activity-ktx & fragment-ktx
    def activity_version = "1.2.0-beta01"
    implementation "androidx.activity:activity-ktx:${activity_version}"
    def fragment_version = "1.3.0-beta01"
    implementation "androidx.fragment:fragment-ktx:$fragment_version"
*/
class ImagePickerFragment : Fragment(R.layout.fragment_image_picker) {
    private val mediaPicker =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                Toast.makeText(requireContext(), "Image Uri: $uri", Toast.LENGTH_LONG).show()
                println("Debug: $uri")

                // Maybe this is a better way to distinguish photo from video
                if (uri.toString().contains("image"))
                    imageView.setImageURI(uri)
                else
                    playVideo(uri)
            }

    private lateinit var photoUri : Uri
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            imageView.setImageURI(photoUri)
            println("Debug: photoUri = $photoUri")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // image/* video/* not working. Use */* for now
        attachMediaBtn.setOnClickListener {
            mediaPicker.launch("*/*")
        }

        /*
        In Manifest you need to add <provider> ... </provider>. See Manifest of this project
        Also, need to add file_paths.xml user res/xml (see example in this project)
        More info @ https://developer.android.com/reference/androidx/core/content/FileProvider
        Interesting discussion @ https://stackoverflow.com/questions/21931169/fileprovider-throws-exception-on-geturiforfile/48103567#48103567
        I do not understand it fully but ok for now.
        Bit more complicated than expected 😱 Android people what is! Why complicating it 😲?
        */
        takeMediaBtn.setOnClickListener {
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

    private fun playVideo(videoUri: Uri) {
        try {
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(videoUri)
            videoView.requestFocus()
            videoView.start()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading video ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}