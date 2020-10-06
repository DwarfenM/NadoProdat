package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sell_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts
import kz.sherua.nadoprodat.utils.PreferenceHelper.setBasket

class SalesItemsAdapter(val ctx: Context) : RecyclerView.Adapter<SalesItemHolder>() {

    private var items: MutableList<SalesWithDetailsAndProducts> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesItemHolder {
        return SalesItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.sales_item,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SalesItemHolder, position: Int) {
        holder.tvItemName.text = items[position].sales.id.toString()
    }

//    fun addItems(itemsToAdd: List<Product>) {
//        items = itemsToAdd.toMutableList()
//        notifyDataSetChanged()
//    }

    fun getProducts() : List<SalesWithDetailsAndProducts> {
        return items
    }
}

class SalesItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.tvItemName
    val tvItemPriceXCount = view.tvItemPriceXCount
    val tvItemPrice = view.tvItemPrice
    val imgItem = view.imgItem
    val btnDeleteItem = view.btnDeleteItem
    val btnAddItem = view.btnAddItem
    val btnRemoveItem = view.btnRemoveItem
    val tvCount = view.tvCount

}