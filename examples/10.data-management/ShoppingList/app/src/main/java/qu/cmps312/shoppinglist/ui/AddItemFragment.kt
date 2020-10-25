package qu.cmps312.shoppinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.entity.Item
import kotlinx.android.synthetic.main.fragment_add_item.*
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingViewModel

// User DialogFragment instead of Fragment
// Also, the nav graph uses dialog instead of fragment
class AddItemFragment : DialogFragment() {
    private val shoppingViewModel by activityViewModels<ShoppingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton.setOnClickListener { onAddItem() }
        cancelButton.setOnClickListener { dismiss() }
    }

    private fun onAddItem() {
        if (itemSp.selectedItem.toString() == "Select item") {
            (itemSp.selectedView as TextView).error = "Select item"
            itemSp.requestFocusFromTouch()
            return
        }
        if (quantityEt.text.toString().isEmpty()) {
            quantityEt.error = "Enter quantity"
            quantityEt.requestFocusFromTouch()
            return
        }

        val item = Item(name = itemSp.selectedItem.toString(),
                        quantity = quantityEt.text.toString().toInt())
        shoppingViewModel.addItem(item)

        // Clear the views to allow another entry
        itemSp.setSelection(0)
        quantityEt.text.clear()
    }
}