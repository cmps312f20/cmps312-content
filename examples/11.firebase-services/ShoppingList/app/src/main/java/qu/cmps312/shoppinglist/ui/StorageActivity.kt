package qu.cmps312.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.ui.adapter.ImageAdapter

private const val REQUEST_CODE_IMAGE_PICK = 0

class StorageActivity : AppCompatActivity() {

    var curFile: Uri? = null

    val storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        ivImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        btnUploadImage.setOnClickListener {
            uploadImageToStorage("myImage")
        }

        btnDownloadImage.setOnClickListener {
            downloadImage("myImage")
        }

        btnDeleteImage.setOnClickListener {
            deleteImage("myImage")
        }

        listFiles()
    }

    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val images = storageRef.child("images/").listAll().await()
            val imageUrls = mutableListOf<String>()
            for(image in images.items) {
                val url = image.downloadUrl.await()
                imageUrls.add(url.toString())
            }
            withContext(Dispatchers.Main) {
                val imageAdapter = ImageAdapter(imageUrls)
                rvImages.apply {
                    adapter = imageAdapter
                    layoutManager = LinearLayoutManager(this@StorageActivity)
                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@StorageActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteImage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            storageRef.child("images/$filename").delete().await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@StorageActivity, "Successfully deleted image.",
                    Toast.LENGTH_LONG).show()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@StorageActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun downloadImage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val bytes = storageRef.child("images/$filename").getBytes(maxDownloadSize).await()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            withContext(Dispatchers.Main) {
                ivImage.setImageBitmap(bmp)
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@StorageActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadImageToStorage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            curFile?.let {
                storageRef.child("images/$filename").putFile(it).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@StorageActivity, "Successfully uploaded image",
                            Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@StorageActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK) {
            data?.data?.let {
                curFile = it
                ivImage.setImageURI(it)
            }
        }
    }

}
