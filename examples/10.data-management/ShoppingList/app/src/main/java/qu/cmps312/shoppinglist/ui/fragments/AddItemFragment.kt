package qu.cmps312.shoppinglist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.entity.Item
import qu.cmps312.shoppinglist.entity.Product
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingViewModel


// User DialogFragment instead of Fragment
// Also, the nav graph uses dialog instead of fragment
class AddItemFragment : DialogFragment() {
    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val products = shoppingViewModel.getProducts(1)
            val adapter = ArrayAdapter<Product>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, products
            )
            productSp.adapter = adapter
        }

        addButton.setOnClickListener { onAddItem() }
        cancelButton.setOnClickListener { dismiss() }
    }

    private fun onAddItem() {
        if (productSp.selectedItem.toString() == "Select item") {
            (productSp.selectedView as TextView).error = "Select item"
            productSp.requestFocusFromTouch()
            return
        }
        if (quantityEt.text.toString().isEmpty()) {
            quantityEt.error = "Enter quantity"
            quantityEt.requestFocusFromTouch()
            return
        }

        val selectedProduct = productSp.selectedItem as Product
        val quantity =  quantityEt.text.toString().toInt()

        val item = Item(selectedProduct.id, quantity)
        shoppingViewModel.addItem(item)

        // Clear the views to allow another entry
        productSp.setSelection(0)
        quantityEt.text.clear()
    }
}