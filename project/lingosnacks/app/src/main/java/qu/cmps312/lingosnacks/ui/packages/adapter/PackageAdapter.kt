package qu.cmps312.lingosnacks.ui.packages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.databinding.PackageItemBinding
import qu.cmps312.lingosnacks.model.LearningPackage
import qu.cmps312.lingosnacks.ui.viewmodel.UserInfo

enum class PackageAction {
    View, Update, Delete, Download, Ratings, Rate, MatchDefinition, UnscrambleSentence
}

class PackageAdapter(
    val packageActionListener: (LearningPackage, PackageAction) -> Unit,
) : RecyclerView.Adapter<PackageAdapter.PackageViewHolder>() {

    var learningPackages = listOf<LearningPackage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var userInfo = UserInfo()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PackageViewHolder(private val viewBinding: PackageItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(learningPackage: LearningPackage) {
            viewBinding.learningPackage = learningPackage
            viewBinding.userInfo = userInfo

            viewBinding.root.viewBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.View)
            }

            viewBinding.root.updateBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Update)
            }

            viewBinding.root.deleteBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Delete)
            }

            viewBinding.root.rateBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Rate)
            }

            viewBinding.root.ratingsBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Ratings)
            }

            viewBinding.root.ratingsBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Ratings)
            }

            viewBinding.root.ratingsBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Ratings)
            }

            viewBinding.root.unscrambleSentenceBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.UnscrambleSentence)
            }

            viewBinding.root.matchDefinitionBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.MatchDefinition)
            }

            viewBinding.root.downLoadBtn.setOnClickListener {
                packageActionListener(learningPackage, PackageAction.Download)
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