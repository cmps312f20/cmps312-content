package qu.cmps312.lingosnacks.ui.packages

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
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import qu.cmps312.lingosnacks.ui.packages.adapter.PackageAction
import qu.cmps312.lingosnacks.ui.packages.adapter.PackageAdapter
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageEditorViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel

class PackagesFragment : Fragment(R.layout.fragment_packages) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val packageEditorViewModel by activityViewModels<PackageEditorViewModel>()

    private lateinit var packageAdapter: PackageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        packageAdapter = PackageAdapter(::onPackageAction)
        packagesRv.apply {
            adapter = packageAdapter
            layoutManager = LinearLayoutManager(context)
        }

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
            packageAdapter.userInfo = authViewModel.getCurrentUserInfo()
        }

        floatingActionButton.setOnClickListener {
            if (authViewModel.getCurrentUserInfo().role != "Teacher") {
                Snackbar.make(requireView(), "Need to be a signed in Teacher", Snackbar.LENGTH_LONG).setAction("Login") {
                    findNavController().navigate(R.id.signInFragment)
                }.show()
                return@setOnClickListener
            }
            packageEditorViewModel.learningPackage = LearningPackage()
            findNavController().navigate(R.id.toPackageEditor)
        }
    }

    private fun onPackageAction(learningPackage: LearningPackage, packageAction: PackageAction) {
        packageViewModel.selectedPackage = learningPackage
        val navAction = when (packageAction) {
            PackageAction.Update -> {
                packageEditorViewModel.learningPackage = packageViewModel.selectedPackage!!
                R.id.toPackageEditor
            }
            PackageAction.Rate -> R.id.ratePackageFragment
            PackageAction.Ratings -> R.id.toPackageRatings
            PackageAction.View -> R.id.toFlashCards
            PackageAction.UnscrambleSentence -> R.id.toUnscrambleSentence
            PackageAction.MatchDefinition -> R.id.toMatchDefinition
            PackageAction.Download -> {
                packageViewModel.downloadPackage()
                0
            }
            PackageAction.DeleteOnlinePackage -> {
                packageViewModel.deleteOnlinePackage()
                0
            }
            PackageAction.DeleteLocalPackage -> {
                packageViewModel.deleteLocalPackage()
                0
            }
            else -> 0
        }
        if (navAction != 0)
            findNavController().navigate(navAction)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_package_fragment, menu)
        val searchView = menu.findItem(R.id.mi_search_package)
        (searchView.actionView as SearchView).setOnQueryTextListener( searchHandler() )
    }

    private fun searchHandler()=object : SearchView.OnQueryTextListener {
        //Calls when a user press ok
        override fun onQueryTextSubmit(query: String?) = false

        //calls when the text changes
        override fun onQueryTextChange(searchText: String): Boolean {
            packageViewModel.getPackages(searchText)
            return true
        }
    }
}