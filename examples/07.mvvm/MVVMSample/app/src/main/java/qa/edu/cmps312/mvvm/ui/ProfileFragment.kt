package qa.edu.cmps312.mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import qa.edu.cmps312.mvvm.R
import qa.edu.cmps312.mvvm.databinding.FragmentProfileBinding
import qa.edu.cmps312.mvvm.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain an instance of the view binding class.
        val viewBinding = FragmentProfileBinding.bind(view)
        // Specify the current fragment as the lifecycle owner
        viewBinding.lifecycleOwner = this

        // Obtain a ViewModel instance scoped to activity
        val viewModel: ProfileViewModel by activityViewModels()
        // Bind the View with the viewModel.profile
        viewBinding.profile = viewModel.profile.value

        // when viewModel.profile changes, this observer get notified and re-bind
        // the profile model with the View
        viewModel.profile.observe(requireActivity()) {
            viewBinding.profile = it //<- re-binding profile
        }
        // Listen to saveBtn onClick event
        viewBinding.saveBtn.setOnClickListener {
            viewModel.save(viewBinding.profile!!)
        }

        //.resize(100,100)
        //Picasso.get().load(viewModel.profile.value?.photoUrl).into(photoIv)
        //Glide.with(this).load(viewModel.profile.value?.photoUrl).into(photoIv)

        //Watch the fragment cycle events
        val lifeCycleObserver = ActivityFragmentObserver(lifecycle, "ProfileFragment \uD83D\uDC64\uD83D\uDC64")
    }
}