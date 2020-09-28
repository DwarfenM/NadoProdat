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
import kz.sherua.nadoprodat.utils.PreferenceHelper.setBasket

class BasketItemsAdapter(val ctx: Context, val btnSell: Button) : RecyclerView.Adapter<BasketItemHolder>() {

    private var items: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemHolder {
        return BasketItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.sell_item,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BasketItemHolder, position: Int) {
        holder.tvItemName.text = items[position].name
        holder.tvItemPrice.text = (items[position].salesPrice * items[position].count).toString()
        holder.tvCount.text = items[position].count.toString()
        holder.tvItemPriceXCount.text = items[position].count.toString() + " x " + items[position].salesPrice.toString()
        holder.btnAddItem.setOnClickListener{
            items[position].count += 1
            setBasket(items, ctx)
            btnSell.text = "Заработать " + items.map { it.salesPrice * it.count }.sum()
            notifyDataSetChanged()
        }
        holder.btnRemoveItem.setOnClickListener{
            items[position].count -= 1
            setBasket(items, ctx)
            btnSell.text = "Заработать " + items.map { it.salesPrice * it.count }.sum()
            notifyDataSetChanged()
        }
        holder.btnDeleteItem.setOnClickListener{
            items.removeAt(position)
            setBasket(items, ctx)
            btnSell.text = "Заработать " + items.map { it.salesPrice * it.count }.sum()
            notifyDataSetChanged()
        }
    }

    fun addItems(itemsToAdd: List<Product>) {
        items = itemsToAdd.toMutableList()
        notifyDataSetChanged()
    }

    fun getProducts() : List<Product> {
        return items
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