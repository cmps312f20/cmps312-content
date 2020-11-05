package qu.cmps312.lingosnacks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.package_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.databinding.PackageItemBinding
import qu.cmps312.lingosnacks.model.LearningPackage

class PackageAdapter(
    val ratePackageListener: (LearningPackage) -> Unit,
    val deletePackageListener: (LearningPackage) -> Unit,
    val updatePackageListener: (LearningPackage) -> Unit,
    val getPackageRatings: (LearningPackage) -> Unit
) : RecyclerView.Adapter<PackageAdapter.PackageViewHolder>() {

    var learningPackages = listOf<LearningPackage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var currentUserEmail = ""
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PackageViewHolder(private val viewBinding: PackageItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(learningPackage: LearningPackage) {
            viewBinding.learningPackage = learningPackage
            viewBinding.currentUserEmail = currentUserEmail

            viewBinding.root.rateBtn.setOnClickListener {
                ratePackageListener(learningPackage)
            }
            viewBinding.root.deleteBtn.setOnClickListener {
                deletePackageListener(learningPackage)
            }
            viewBinding.root.updateBtn.setOnClickListener {
                updatePackageListener(learningPackage)
            }
            viewBinding.root.ratingsBtn.setOnClickListener {
                getPackageRatings(learningPackage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val viewBinding: PackageItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.package_item, parent, false)

        return PackageViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) =
            holder.bind(learningPackages[position])

    override fun getItemCount() = learningPackages.size
}