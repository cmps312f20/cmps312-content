package qa.edu.cmps312.navigation.common
import android.content.Context
import android.widget.Toast

// Helper classes and functions
fun Context?.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context?.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, this?.resources?.getText(resId), duration).show()
}