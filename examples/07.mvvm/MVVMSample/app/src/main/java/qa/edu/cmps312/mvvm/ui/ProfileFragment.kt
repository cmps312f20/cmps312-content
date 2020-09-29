package qa.edu.cmps312.mvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.databinding.FragmentProfileBinding
import qa.edu.cmps312.mvvm.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the view layout and obtain an instance of the binding class.
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        // Specify the current fragment as the lifecycle owner
        binding.lifecycleOwner = this

        // Obtain ViewModel scoped with main activity
        //val viewModel: ProfileViewModel by activityViewModels()
        val viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        // An alternative ViewModel using Observable fields and @Bindable properties can be used:
        //val viewModel = ViewModelProviders.of(this).get(ProfileObservableViewModel::class.java)

        // when viewModel.profile changes, this observer get notified and re-bind
        // the profile model with the layout.
        viewModel.profile.observe(requireActivity()) {
            binding.profile = it //<- re-binding user
        }
        // Bind layout with ViewModel
        binding.profile = viewModel.profile.value

        binding.saveBtn.setOnClickListener {
            viewModel.save(binding.profile!!)
        }

        //Watch the fragment cycle events
        val lifeCycleObserver = ActivityFragmentObserver(lifecycle, "ProfileFragment \uD83D\uDC64\uD83D\uDC64")
        return binding.root
    }
}