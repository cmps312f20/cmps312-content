package qa.edu.cmps312.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.databinding.FragmentProfileBinding
import qa.edu.cmps312.mvvm.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
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
            Log.d("ProfileActivity.saveBtn", binding.profile.toString())
            //ToDo: pass it to the view model to save it to DB
            //viewModel.profile
        }

        return binding.root
    }
}