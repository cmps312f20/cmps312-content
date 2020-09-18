package qa.edu.cmps312.countryexplorer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import json.country.Country
import kotlinx.android.synthetic.main.list_item_country.view.*
import qa.edu.cmps312.countryexplorer.R
import kotlin.math.ln
import kotlin.math.pow

enum class SortBy { NAME, POPULATION, NAME_DESC, POPULATION_DESC }

// Adapted from https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp
fun withSuffix(count: Long): String? {
    if (count < 1000) return "" + count
    // ln is log
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format(
        "%.1f %c",
        count / 1000.0.pow(exp.toDouble()),
        "kMBTPE"[exp - 1]
    )
}

/*
The adapter marries countries list to the Recycler view
 */
class CountryAdapter(private val countries: List<Country>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private lateinit var countryFilteredList : List<Country>

    init {
        countryFilteredList = countries
    }

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
                viewHolder.bind(countryFilteredList[position])
    }

    override fun getItemCount() = countryFilteredList.size

    fun filter(continent: String) {
        countryFilteredList = if (continent.isEmpty()) {
            countries
        } else {
            countries.filter { it.continent.equals(continent, true) }
        }
        notifyDataSetChanged()
    }

   fun sort(sortBy: SortBy) {
        countryFilteredList = when (sortBy) {
            SortBy.NAME -> countryFilteredList.sortedBy { it.name }
            SortBy.POPULATION -> countryFilteredList.sortedBy { it.population }
            SortBy.NAME_DESC -> countryFilteredList.sortedByDescending { it.name }
            SortBy.POPULATION_DESC -> countryFilteredList.sortedByDescending { it.population }
        }
        notifyDataSetChanged()
    }

    // Could be a normal separate class but it is common to make it an inner class
    // As it is only used in this Adapter class and it can access properties and
    // methods of the outer class if needed
    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country) {
            itemView.apply {
                nameTv.text = country.name
                capitalTv.text = country.capital
                populationTv.text = withSuffix(country.population)

                 val image = resources.getIdentifier(
                    "flag_${country.code.toLowerCase()}",
                    "drawable",
                    context.packageName
                )
                flagIv.setImageResource(image)
            }
        }
    }
}