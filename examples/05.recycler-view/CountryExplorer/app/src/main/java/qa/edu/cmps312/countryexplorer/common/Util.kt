package qa.edu.cmps312.countryexplorer.common
import android.content.Context
import android.widget.Toast
import kotlin.math.ln
import kotlin.math.pow

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, this.resources.getText(resId), duration).show()
}

// Adapted from https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp
fun withSuffix(count: Long): String {
    if (count < 1000) return count.toString()
    // ln is log
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f %c",
        count / 1000.0.pow(exp.toDouble()),
        "kMBTPE"[exp - 1]
    )
}