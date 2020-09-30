package qa.edu.cmps312.mvvm.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.model.Seniority

//object BindingAdapters {
    /**
     * A Binding Adapter that is called whenever the value of the attribute `app:popularityIcon`
     * changes. Receives a popularity level that determines the icon and tint color to use.
     */
    @BindingAdapter("app:seniorityIcon")
    fun ImageView.seniorityIcon(seniority: Seniority) {
        val color = getSeniorityColor(seniority, this.context)
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
        this.setImageDrawable(getSeniorityIcon(seniority, this.context))
    }


    /**
     * Binding Adapter that hides a view if the number is zero.
     */
    @BindingAdapter("app:hideIfZero")
    fun View.hideIfZero(value: Int) {
        this.visibility = if (value == 0) View.GONE else View.VISIBLE
    }

    // Load an image into an ImageView from a Url
    // This requires adding <uses-permission android:name="android.permission.INTERNET" /> to the manifest
    @BindingAdapter("app:imageUrl")
    fun ImageView.loadImage(url: String) {
        Glide.with(this)
             .load(url)
             .into(this)
        //Picasso.get().load(url).into(this)
    }

    private fun getSeniorityColor(seniority: Seniority, context: Context): Int {
        return when (seniority) {
            Seniority.FRESHMAN -> ContextCompat.getColor(context, R.color.freshman)
            Seniority.JUNIOR -> ContextCompat.getColor(context, R.color.junior)
            Seniority.SENIOR -> ContextCompat.getColor(context, R.color.senior)
        }
    }

    private fun getSeniorityIcon(seniority: Seniority, context: Context): Drawable? {
        return when (seniority) {
            Seniority.FRESHMAN -> {
                ContextCompat.getDrawable(context, R.drawable.ic_freshmap)
            }
            Seniority.JUNIOR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_person)
            }
            Seniority.SENIOR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_senior)
            }
        }
    }