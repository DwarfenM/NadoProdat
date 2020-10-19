package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sales_collapsed_parent.view.*
import kotlinx.android.synthetic.main.sales_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.SalesDetailsWithProducts

class SingleSaleItemsAdapter(val ctx: Context) : RecyclerView.Adapter<SingleSaleItemsHolder>() {

    private var items: MutableList<SalesDetailsWithProducts> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleSaleItemsHolder {
       return SingleSaleItemsHolder(
           LayoutInflater.from(parent.context)
           .inflate(
               R.layout.sales_item,
               parent,
               false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SingleSaleItemsHolder, position: Int) {
        holder.tvItemName.text = items[position].product.product.name
        holder.tvPriceXCount.text = items[position].salesDetails.productCount.toString() + " x " + items[position].product.product.salesPrice.toString()
        holder.tvItemPrice.text = items[position].salesDetails.productSalesPrice.toString()
    }

    fun addItems(itemsToAdd: MutableList<SalesDetailsWithProducts>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }

}

class SingleSaleItemsHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.tvItemName
    val tvPriceXCount = view.tvItemPriceXCount
    val tvItemPrice = view.tvItemPrice
}