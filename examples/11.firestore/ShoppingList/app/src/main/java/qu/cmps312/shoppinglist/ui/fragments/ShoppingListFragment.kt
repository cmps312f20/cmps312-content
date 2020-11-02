package qu.cmps312.shoppinglist.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.ui.adapter.ShoppingListAdapter
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingListViewModel

class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingListAdapter = ShoppingListAdapter(::onItemDeleted, ::onQuantityChanged)

        shoppingListViewModel.shoppingList.observe(viewLifecycleOwner) {
            shoppingListAdapter.items = it
        }

        shoppingListViewModel.currentUser.observe(requireActivity()) {
            println(">> Debug:  shoppingListViewModel.currentUser change $it")
            shoppingListViewModel.getShoppingItems()
        }

        swipeToRefresh.setOnRefreshListener {
            //shoppingViewModel.getItems()
            swipeToRefresh.isRefreshing = false
        }

        itemsRv.apply {
            adapter = shoppingListAdapter
            layoutManager = LinearLayoutManager(view.context)
        }

        addItemBtn.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                Toast.makeText(requireContext(), "Login first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            view.findNavController().navigate(R.id.toAddItem)
        }
    }

    private fun onItemDeleted(item: ShoppingItem) {
        shoppingListViewModel.deleteItem(item)

        Snackbar.make(requireView(), "${item.productName} removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
            shoppingListViewModel.addItem(item)
        }.show()
    }

    private fun onQuantityChanged(item: ShoppingItem) {
        shoppingListViewModel.updateQuantity(item)
    }
}