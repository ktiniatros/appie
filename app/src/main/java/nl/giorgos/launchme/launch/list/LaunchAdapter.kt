package nl.giorgos.launchme.launch.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.launch_item_view.view.titleView
import nl.giorgos.launchme.R
import nl.giorgos.launchme.launch.api.Launch

class LaunchAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<LaunchAdapter.ItemViewHolder>() {

    private var items = emptyList<Launch>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.launch_item_view, parent, false)
        return ItemViewHolder(itemView, itemClickListener)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (items.size > position) {

            holder.titleView.text = items.get(position).name
        }
    }

    fun setItems(items: List<Launch>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View, val itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.titleView

        init {
            itemView.setOnClickListener {
                itemClickListener.clickedItemOnPosition(adapterPosition)
            }
        }
    }
}