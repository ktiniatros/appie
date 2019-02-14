package nl.giorgos.launchme.launch.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import nl.giorgos.launchme.R
import nl.giorgos.launchme.databinding.LaunchItemViewBinding
import nl.giorgos.launchme.launch.api.Launch

class LaunchAdapter: RecyclerView.Adapter<LaunchAdapter.ItemViewHolder>() {

    private var items = ArrayList<Launch>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (items.size > position) {
            holder.bind(items[position])
        }
    }

    class ItemViewHolder(
        private val parent: ViewGroup) : RecyclerView.ViewHolder(parent) {

        fun bind(item: Launch) {
//            binding.item = item
        }
    }
}