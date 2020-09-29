package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.search_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps

class SearchItemsAdapter(val ctx: Context, val addItemTrigger: BehaviorSubject<ProductWithProps>) : RecyclerView.Adapter<SearchItemHolder>()  {

    private var items: MutableList<ProductWithProps> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder {
        return SearchItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.search_item,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        holder.tvItemName.text = items[position].product.name
        holder.tvItemPrice.text = items[position].product.salesPrice.toString()
        holder.tvItemPriceXCount.text = "1 x"  + items[position].product.salesPrice.toString() + " Т"
        holder.btnAddItem.setOnClickListener {
            addItemTrigger.onNext(items[position])
        }
    }

    fun addItems(itemsToAdd: List<ProductWithProps>) {
        items = itemsToAdd.toMutableList()
        notifyDataSetChanged()
    }
}

class SearchItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.tvItemName
    val tvItemPriceXCount = view.tvItemPriceXCount
    val tvItemPrice = view.tvItemPrice
    val imgItem = view.imgItem
    val btnAddItem = view.btnAddItem
}