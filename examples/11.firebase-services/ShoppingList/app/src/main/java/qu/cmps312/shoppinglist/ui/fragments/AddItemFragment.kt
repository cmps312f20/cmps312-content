package qu.cmps312.shoppinglist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_add_item.*
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.entity.Product
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingItemViewModel


// User DialogFragment instead of Fragment
// Also, the nav graph uses dialog instead of fragment
class AddItemFragment : DialogFragment() {
    private val itemViewModel by activityViewModels<ShoppingItemViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Make the dialog box wider
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel.categories.observe(requireActivity()) {
            val adapter = ArrayAdapter<Category>(
                requireContext(),
                R.layout.spinner_text, it
            )
            categorySp.adapter = adapter
        }

        categorySp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedCategory = categorySp.selectedItem as Category
                    itemViewModel.selectedCategory.value = selectedCategory
            }
        }

        val adapter = ArrayAdapter<Product>(
            requireContext(),
            R.layout.spinner_text, mutableListOf()
        )
        productSp.adapter = adapter

        itemViewModel.products.observe(requireActivity()) {
            adapter.clear()
            // Add an empty entry in the spinner
            adapter.add(Product("", "", ""))
            adapter.addAll(it)
            adapter.notifyDataSetChanged()
        }

        addButton.setOnClickListener { onAddItem() }
        cancelButton.setOnClickListener { dismiss() }
    }

    private fun onAddItem() {
        val selectedProduct = productSp.selectedItem as Product
        if (selectedProduct.id.isEmpty()) {
            (productSp.selectedView as TextView).error = "Select product"
            productSp.requestFocusFromTouch()
            return
        }
        if (quantityEt.text.toString().isEmpty()) {
            quantityEt.error = "Enter quantity"
            quantityEt.requestFocusFromTouch()
            return
        }

        val quantity =  quantityEt.text.toString().toInt()

        val item = ShoppingItem(productId = selectedProduct.id,
                                productName = selectedProduct.name, quantity = quantity)
        itemViewModel.addItem(item)

        // Clear the views to allow another entry
        productSp.setSelection(0)
        quantityEt.text.clear()
    }
}