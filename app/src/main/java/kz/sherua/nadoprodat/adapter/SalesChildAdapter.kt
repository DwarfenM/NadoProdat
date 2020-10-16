package kz.sherua.nadoprodat.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sales_collapsed_child.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.fragment.SingleSaleFragment
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
        holder.salesChildView.setOnClickListener {
            val frag = SingleSaleFragment()
            (ctx as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, frag).addToBackStack(null).commit()
        }
    }

    fun addItems(itemsToAdd: MutableList<SalesWithDetailsAndProducts>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }

}
class SalesChildItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.tvItemName
    val tvPrice = view.tvItemPrice
    val tvCxI = view.tvItemPriceXCount
    val salesChildView = view.salesChildView
}