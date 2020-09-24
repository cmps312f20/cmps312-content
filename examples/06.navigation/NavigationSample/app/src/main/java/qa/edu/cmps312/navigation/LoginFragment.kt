package qa.edu.cmps312.navigation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import qa.edu.cmps312.navigation.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginBtn.setOnClickListener {
            val userName = userNameEt.text.toString()
            val action = LoginFragmentDirections.toWelcome(userName)
            findNavController().navigate(action)
            //val bundle = bundleOf("userName" to userName)
            //findNavController().navigate(R.id.navigateToWelcome, bundle)
        }
    }
}