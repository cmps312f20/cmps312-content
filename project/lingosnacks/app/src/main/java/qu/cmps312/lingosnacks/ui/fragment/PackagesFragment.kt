package qu.cmps312.lingosnacks.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.LearningPackage
import kotlinx.android.synthetic.main.fragment_packages.*
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import qu.cmps312.lingosnacks.ui.adapter.PackageAdapter
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel

class PackagesFragment : Fragment(R.layout.fragment_packages) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    var currentUserEmail = ""
    private lateinit var packageAdapter: PackageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initRecyclerView()

        // Init packages
        packageViewModel.getPackages()

        swipeToRefresh.setOnRefreshListener {
            packageViewModel.getPackages()
            swipeToRefresh.isRefreshing = false
        }

        packageViewModel.packages.observe(viewLifecycleOwner) {
            println(">> Debug: packageViewModel.packages.observe: ${it.size}")
            packageAdapter.learningPackages = it
        }

        authViewModel.currentUser.observe(viewLifecycleOwner) {
            println(">> Debug: authViewModel.currentUser.observe: $it")
            currentUserEmail = it?.email ?: ""
            packageAdapter.currentUserEmail = currentUserEmail
        }

        floatingActionButton.setOnClickListener {
            if (currentUserEmail.isEmpty()) {
                Snackbar.make(requireView(), "Need to login first", Snackbar.LENGTH_LONG).setAction("Login") {
                    findNavController().navigate(R.id.signInFragment)
                }.show()
                return@setOnClickListener
            }
            packageViewModel.selectedPackage = null
            //findNavController().navigate(R.id.toPackageEditor)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_package_fragment, menu)
        val searchView = menu.findItem(R.id.mi_search_package)
        (searchView.actionView as SearchView).setOnQueryTextListener( searchHandler() )
    }

    private fun onGetPackageRatings(learningPackage: LearningPackage) {
        packageViewModel.selectedPackage = learningPackage
        findNavController().navigate(R.id.toPackageRatings)
    }

    //region private methods

    //Send the user from learning packages to ratings fragment
    private fun onRatePackage(learningPackage: LearningPackage) {
        //findNavController().navigate(R.id.toPackageRating)
        /*val action =LearningPackagesFragmentDirections.actionLearningPackagesFragmentToRatingsFragment(
                learningPackage.ratings.toTypedArray())

        findNavController().navigate(action)*/
    }

    private fun onDeletePackage(learningPackage: LearningPackage) {
        packageViewModel.deletePackage(learningPackage)
    }

    private fun onUpdatePackage(learningPackage: LearningPackage) {
        packageViewModel.selectedPackage = learningPackage
        //findNavController().navigate(R.id.toPackageEditor)
    }

    //Init the recycler view
    private fun initRecyclerView() {
        packageAdapter = PackageAdapter(::onRatePackage,::onDeletePackage,::onUpdatePackage, ::onGetPackageRatings)
        packagesRv.apply {
            adapter = packageAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    //Creates a search handler
    private fun searchHandler()=object : SearchView.OnQueryTextListener {
        //Calls when a user press ok
        override fun onQueryTextSubmit(query: String?) = false

        //calls when the text changes
        override fun onQueryTextChange(searchText: String): Boolean {
            packageViewModel.getPackages(searchText)
            return true
        }
    }
    //endregion
}