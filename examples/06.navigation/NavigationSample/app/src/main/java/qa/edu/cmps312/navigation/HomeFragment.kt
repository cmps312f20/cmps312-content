package qa.edu.cmps312.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.firstTv.setOnClickListener {
            findNavController().navigate(R.id.toSecondFragment)
            // Or
            //findNavController().navigate(R.id.secondFragment)
        }
    }
}