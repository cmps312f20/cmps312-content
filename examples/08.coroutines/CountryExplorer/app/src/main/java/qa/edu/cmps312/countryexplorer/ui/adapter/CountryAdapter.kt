package qa.edu.cmps312.countryexplorer.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.snackbar.Snackbar
import json.country.Country
import kotlinx.android.synthetic.main.list_item_country.view.*
import qa.edu.cmps312.countryexplorer.R
import qa.edu.cmps312.countryexplorer.ui.common.withSuffix


enum class SortBy { NAME, POPULATION, NAME_DESC, POPULATION_DESC }

/*
   RecyclerView adapter connects list to the Recycler View
 */
class CountryAdapter(private val activity: Activity,
    private val clickListener: (country: Country) -> Unit,
    private val deleteListener: (viewHolder: RecyclerView.ViewHolder) -> Unit
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var countries = mutableListOf<Country>()
        set(value) {
            field = value
            countryFilteredList = value
            notifyDataSetChanged()
        }

    private var countryFilteredList = mutableListOf<Country>()

    //region RecyclerView.Adapter methods: onCreateViewHolder, onBindViewHolder, getItemCount
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Create an item view from list item layout
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_country,
            parent,
            false
        )
        // Place the layout inside a ViewHolder
        return CountryViewHolder(itemView)
    }

    /*
    Runtime calls adapter the onBindViewHolder to bind a list item to a row on the screen
    Write data from list[position] into viewHolder
     */
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
         (viewHolder as CountryViewHolder).bind(countryFilteredList[position])
    }

    override fun getItemCount() = countryFilteredList.size
    //endregion

    //region Interaction handling methods: filter, sort, remove
    fun filter(searchText: String) {
        countryFilteredList = if (searchText.isEmpty()) {
            countries
        } else {
            countries.filter { it.region.contains(searchText, true) or
                               it.name.contains(searchText, true) or
                               it.capital.contains(searchText, true)
                               // ot it.cioc.contains(searchText, true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

   fun sort(sortBy: SortBy) {
        countryFilteredList = when (sortBy) {
            SortBy.NAME -> countryFilteredList.sortedBy { it.name }.toMutableList()
            SortBy.POPULATION -> countryFilteredList.sortedBy { it.population }.toMutableList()
            SortBy.NAME_DESC -> countryFilteredList.sortedByDescending { it.name }.toMutableList()
            SortBy.POPULATION_DESC -> countryFilteredList.sortedByDescending { it.population }
                .toMutableList()
        }
        // Caused a refresh of the RecyclerView
        notifyDataSetChanged()
    }

    fun deleteCountry(viewHolder: RecyclerView.ViewHolder) {
        // Get the position of the item that was swiped
        val position = viewHolder.adapterPosition
        val deletedCountry = countryFilteredList[position]

        //Remove from filtered list
        countryFilteredList.removeAt(position)
        //Remove from original list
        countries.removeIf { it.cioc == deletedCountry.cioc }

        // Inform the RecyclerView adapter that an item has been removed at a specific position.
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "${deletedCountry.name} removed", Snackbar.LENGTH_LONG).setAction(
            "UNDO"
        ) {
            countryFilteredList.add(position, deletedCountry)
            countries.add(deletedCountry)
            notifyItemInserted(position)
        }.show()
    }
    //endregion

    //region CountryViewHolder class
    // Could be a normal separate class but it is common to make it an inner class
    // As it is only used in this Adapter class and it can access properties and
    // methods of the outer class if needed
    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country) {
            itemView.deleteBtn.setOnClickListener {
                deleteListener(this)
            }

            /* setOnClickListener on the itemView and call the
               clickListener supplied by the Activity. Pass the country
               object to the clickListener.
               As parameter, the listener gets the data list element (i.e, country) that
               was clicked, to react accordingly
            */
            itemView.setOnClickListener {
                clickListener(country)
            }

            itemView.apply {
                // Write data from country object to the Country View
                nameTv.text = country.name
                capitalTv.text = country.capital
                populationTv.text = withSuffix(country.population)

                flagIv.load(country.flag)
            }
        }
    }
    //endregion
}