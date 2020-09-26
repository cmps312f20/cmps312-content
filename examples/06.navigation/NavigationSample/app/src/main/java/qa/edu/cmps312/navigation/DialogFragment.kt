package qa.edu.cmps312.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_dialog.*
import qa.edu.cmps312.navigation.common.toast

class DialogFragment : Fragment(R.layout.fragment_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        alertDialogBtn.setOnClickListener { alertDialog() }
        simpleDialogBtn.setOnClickListener { simpleDialog() }
        singleChoiceConfirmationDialogBtn.setOnClickListener { singleChoiceConfirmationDialog() }
        multiChoiceConfirmationDialogBtn.setOnClickListener { multiChoiceConfirmationDialog() }
    }

    private fun alertDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Discard draft?")
            .setMessage("This will permanently delete the current e-mail draft.")
            .setPositiveButton("Discard") { dialog, which ->
                context.toast("Clicked discard")
                //or
                //Toast.makeText(activity, "Clicked discard", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                context.toast("Clicked cancel")
            }
            .show()
    }

    private fun simpleDialog() {
        val items = arrayOf("user01@gmail.com", "user02@gmail.com", "Add account")
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Set backup account")
            .setItems(items) { dialog, which ->
                context.toast("Clicked ${items[which]}")
            }
            .show()
    }

    private fun singleChoiceConfirmationDialog() {
        val items = arrayOf("None", "Callisto", "Ganymede", "Luna")
        val checkedItem = 1
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Phone ringtone")
            .setSingleChoiceItems(items, checkedItem) { dialog, which ->
                context.toast( "Chose ${items[which]}")
            }
            .setPositiveButton("Ok") { dialog, which ->
                context.toast("Clicked ok")
            }
            .setNegativeButton("Cancel") { dialog, which ->
                context.toast( "Clicked cancel")
            }
            .show()
    }

    private fun multiChoiceConfirmationDialog() {
        val items = arrayOf("None", "Forums", "Social", "Updates")
        val checkedItems = booleanArrayOf(true, false, false, false)
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Label as:")
            .setMultiChoiceItems(items, checkedItems) { dialog, which, checked ->
                context.toast("Chose ${items[which]} - $checked")
            }
            .setPositiveButton("Ok") { dialog, which ->
                context.toast("Clicked ok")
            }
            .setNegativeButton("Cancel") { dialog, which ->
                context.toast("Clicked cancel")
            }
            .setIcon(R.drawable.ic_android_color_control)
            .show()
    }
}
