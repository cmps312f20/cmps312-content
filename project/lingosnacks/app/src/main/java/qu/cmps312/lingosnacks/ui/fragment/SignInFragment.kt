package qu.cmps312.lingosnacks.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_signin.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel

fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

class SignInFragment : DialogFragment() {
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInBtn.setOnClickListener {
            if (!validateInput()) return@setOnClickListener

            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()
            val user = authViewModel.signIn(email, password)
            if (user == null) {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            } else {
                dismiss()
            }
        }
        signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }

    private fun validateInput() : Boolean {
        if (!emailEt.text.toString().trim().isValidEmail()) {
            emailEt.error = "Enter email"
            emailEt.requestFocusFromTouch()
            return false
        }

        if (passwordEt.text.toString().trim().isEmpty()) {
            emailEt.error = "Enter password"
            emailEt.requestFocusFromTouch()
            return false
        }
        return true
    }
}