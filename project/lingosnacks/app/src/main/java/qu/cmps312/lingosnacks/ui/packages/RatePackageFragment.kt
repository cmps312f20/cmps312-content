package qu.cmps312.lingosnacks.ui.packages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_rate_package.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Rating
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel
import java.text.SimpleDateFormat
import java.util.*

class RatePackageFragment : DialogFragment() {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rate_package, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitBtn.setOnClickListener {
            if (!validateInput()) return@setOnClickListener
            val packageId = packageViewModel.selectedPackage?.packageId.toString()
            val doneBy = authViewModel.currentUser.value?.email.toString()
            val currentDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa").format(Date())

            val newRating = Rating(
                "", packageId, commentEt.text.toString(), currentDate,
                doneBy, ratingBar.rating.toDouble()
            )

            packageViewModel.addRating(newRating)
            dismiss()
        }
    }

    private fun validateInput() : Boolean {
        if (commentEt.text.toString().trim().isEmpty()) {
            commentEt.error = "Enter comment"
            commentEt.requestFocusFromTouch()
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        // Make the dialog box wider
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}