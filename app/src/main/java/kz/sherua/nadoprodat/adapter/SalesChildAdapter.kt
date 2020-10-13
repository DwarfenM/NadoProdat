package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sales_collapsed_child.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts

class SalesChildItemsAdapter(val ctx: Context) : RecyclerView.Adapter<SalesChildItemHolder>() {

    private var items: MutableList<SalesWithDetailsAndProducts> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesChildItemHolder {
        return SalesChildItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.sales_collapsed_child,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SalesChildItemHolder, position: Int) {
        holder.tvName.text = "Продажа $position"
        holder.tvPrice.text = items[position].sales.salesPrice.toString()
    }

    fun addItems(itemsToAdd: MutableList<SalesWithDetailsAndProducts>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }

}
class SalesChildItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.tvItemName
    val tvPrice = view.tvItemPrice
}