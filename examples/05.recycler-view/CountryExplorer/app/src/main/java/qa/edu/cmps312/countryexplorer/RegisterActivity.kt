package qa.edu.cmps312.countryexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

// You can pass the layoutId to the constructor
/*
Must add this dependency to app build.gradle
    def activity_version = "1.1.0"
    implementation "androidx.activity:activity-ktx:$activity_version"
*/
class RegisterActivity : AppCompatActivity(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        // Read data sent by the caller
        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")

        toast("Received id: $id & name: $name", Toast.LENGTH_LONG)

        val universities = listOf("Qatar University", "Carnegie Mellon University in Qatar"
            , "Texas A&M University at Qatar"
            , "Hamad Bin Khalifa University (HBKU)")

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, universities
        )
        countriesSp.adapter = adapter
    }
}