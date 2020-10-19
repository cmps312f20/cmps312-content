package qu.cmps312.projecttips

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.projecttips.R
import kotlinx.android.synthetic.main.fragment_image_picker.*

/* Must add the following dependencies to build.gradle
   // Kotlin extensions - activity-ktx & fragment-ktx
    def activity_version = "1.2.0-beta01"
    implementation "androidx.activity:activity-ktx:${activity_version}"
    def fragment_version = "1.3.0-beta01"
    implementation "androidx.fragment:fragment-ktx:$fragment_version"
*/
class ImagePickerFragment : Fragment(R.layout.fragment_image_picker) {
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Toast.makeText(requireContext(), "Image Uri: $uri", Toast.LENGTH_LONG).show()
            imageView.setImageURI(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectImageBtn.setOnClickListener {
            imagePicker.launch("image/*")
        }
    }
}