package qu.cmps312.shoppinglist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.entity.Item

class ShoppingListAdapter(private val deleteListener: (Item) -> Unit,
                          private val quantityChangedListener: (Item) -> Unit)
    : RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder>(){

    var items = listOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Item){
            itemView.apply {
                nameTv.text = item.productName
                quantityTv.text = item.quantity.toString()
            }

            itemView.deleteBtn.setOnClickListener {
                deleteListener(item)
            }

            itemView.increaseBtn.setOnClickListener {
                item.quantity++
                quantityChangedListener(item)
            }

            itemView.decreaseBtn.setOnClickListener {
                item.quantity--
                quantityChangedListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}