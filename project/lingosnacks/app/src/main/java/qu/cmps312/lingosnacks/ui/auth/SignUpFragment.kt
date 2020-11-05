package qu.cmps312.lingosnacks.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_signup.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.User
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel

class SignUpFragment : DialogFragment() {
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn.setOnClickListener {
            signUp()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        // Make the dialog box wider
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun signUp() {
        val firstName = firstNameEt.text.toString().trim()
        val lastName = lastNameEt.text.toString().trim()
        val email = emailEt.text.toString().toLowerCase().trim()
        val password = passwordEt.text.toString().trim()
        val photoUri = photoUriEt.text.toString().trim()
        val role = roleSp.selectedItem.toString().trim()

        if (
            firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            email.isValidEmail() &&
            password.isNotEmpty() &&
            photoUri.isNotEmpty() &&
            role.isNotEmpty()
        ) {
            val newUser = User("", firstName, lastName, email, password, role, photoUri)
            authViewModel.signUp(newUser)
            Toast.makeText(context, "Sign up completed successfully", Toast.LENGTH_SHORT).show()
            dismiss()
        } else {
            Toast.makeText(context, "All fields are Required", Toast.LENGTH_SHORT).show()
        }
    }
}