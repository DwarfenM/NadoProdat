package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.storage_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.fragment.SalesFragmentDirections
import kz.sherua.nadoprodat.fragment.StorageFragmentDirections
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps

class StorageItemsAdapter (val ctx: Context) : RecyclerView.Adapter<StorageItemHolder>() {

    private var items: MutableList<ProductWithProps> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageItemHolder {
        return StorageItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.storage_item,
                    parent,
                    false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: StorageItemHolder, position: Int) {
        holder.tvName.text = items[position].product.name
        holder.lvAddInfo.setOnClickListener {
            val productString = Gson().toJson(items[position])
            val navController =
                Navigation.findNavController((ctx as FragmentActivity).findViewById(R.id.nav_host_fragment))
            val action = StorageFragmentDirections.actionStorageFragmentToSingleProductFragment(productString, false)
            navController.navigate(action)
        }
        holder.tvCount.text = items[position].product.count.toString() + " шт."
        holder.tvPrice.text = items[position].product.salesPrice.toString() + " тг."
    }

    fun getItems(): List<ProductWithProps> {
        return items.toList()
    }
    fun addItems(itemsToAdd: MutableList<ProductWithProps>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }

}

class StorageItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.tvItemName
    val tvPrice = view.tvItemPrice
    val lvAddInfo = view.lvAddInfo
    val tvCount = view.tvCount
}