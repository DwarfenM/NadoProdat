package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sales_collapsed_parent.view.*
import kotlinx.android.synthetic.main.sell_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.ParentSalesModel
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts
import kz.sherua.nadoprodat.utils.PreferenceHelper.setBasket
import java.util.*

class SalesItemsAdapter(val ctx: Context) : RecyclerView.Adapter<SalesItemHolder>() {

    private var items: MutableList<ParentSalesModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesItemHolder {
        return SalesItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.sales_collapsed_parent,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SalesItemHolder, position: Int) {
        holder.tvDate.text = items[position].date.toString()
    }

    fun addItems(itemsToAdd: MutableList<ParentSalesModel>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }
}

class SalesItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val rvAdapter = view.rvChild
    val tvDate = view.tvDate
    val tvSum = view.tvSum
}