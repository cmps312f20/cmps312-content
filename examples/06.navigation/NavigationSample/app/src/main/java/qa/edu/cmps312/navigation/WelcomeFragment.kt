package qa.edu.cmps312.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private val args: WelcomeFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Read data passed from the login fragment
        val userName = args.userName //arguments?.getString("userName")  
        welcomeTv.text = "Welcome $userName"
        okBtn.setOnClickListener {
            findNavController().navigate(R.id.toBlogPosts)
        }
    }
}