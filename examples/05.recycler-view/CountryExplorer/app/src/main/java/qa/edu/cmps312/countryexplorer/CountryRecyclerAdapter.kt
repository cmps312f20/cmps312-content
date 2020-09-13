package qa.edu.cmps312.countryexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import json.country.Country
import kotlinx.android.synthetic.main.list_item_country.view.*

/*
The adapter marries countries list to the Recycler view
 */
class CountryRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    lateinit var countries : List<Country>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_country, parent, false)
        )
    }

    /*
    Runtime calls adapter the onBindViewHolder to bind a list item to a row on the screen
    Write data from list[position] into viewHolder
     */
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            if (viewHolder is CountryViewHolder)
                viewHolder.bind(countries[position])
    }

    override fun getItemCount() = countries.size


}

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(country: Country){
        itemView.apply {
            nameTv.text = country.name
            capitalTv.text = country.capital
            populationTv.text = country.population.toString()

            flagIv.apply {
                // context is the root activity in which flagIv component is displayed
                val imgId = context.resources.getIdentifier(
                    "drawable/flag_${country.code.toLowerCase()}",
                    null,
                    context.packageName
                )
                setImageResource(imgId)
            }
        }
    }
}