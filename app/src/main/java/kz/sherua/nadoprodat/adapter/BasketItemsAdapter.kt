package kz.sherua.nadoprodat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.BasketModel

class BasketItemsAdapter : RecyclerView.Adapter<BasketItemHolder>() {

    private var items: MutableList<BasketModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemHolder {
        return BasketItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.search_item,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BasketItemHolder, position: Int) {
        holder.tvItemName.text = items[position].itemName
        holder.tvItemPrice.text = items[position].itemPrice.toString()
    }

    fun addItems(itemsToAdd: List<BasketModel>) {
        items = itemsToAdd.toMutableList()
        notifyDataSetChanged()
    }
}

class BasketItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.tvItemName
    val tvItemPriceXCount = view.tvItemPriceXCount
    val tvItemPrice = view.tvItemPrice
    val imgItem = view.imgItem
    val btnDeleteItem = view.btnDeleteItem
    val btnAddItem = view.btnAddItem
    val btnRemoveItem = view.btnRemoveItem
    val tvCount = view.tvCount

}