package kz.sherua.nadoprodat.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sales_collapsed_child.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.fragment.SalesFragmentDirections
import kz.sherua.nadoprodat.fragment.SingleSaleFragment
import kz.sherua.nadoprodat.model.SingleSaleItem
import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts
import java.text.SimpleDateFormat
import java.util.*

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
        val date = Date(items[position].sales.crDate)
        val formatter = SimpleDateFormat("HH:mm")
        holder.tvName.text = "Продажа $position"
        holder.tvPrice.text = items[position].sales.salesPrice.toString()
        holder.lvAddInfo.setOnClickListener {
            val listString = Gson().toJson(SingleSaleItem(date,items[position].salesDetails))
            val navController = findNavController((ctx as FragmentActivity).findViewById(R.id.nav_host_fragment))
            val action = SalesFragmentDirections.actionSalesFragmentToSingleSaleFragment(listString)
            navController.navigate(action)
//            (ctx as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, frag).addToBackStack(null).commit()
        }
        holder.tvTime.text = formatter.format(date)
        holder.tvSalesCount.text = items[position].salesDetails.size.toString() + " товаров на сумму:"

    }

    fun addItems(itemsToAdd: MutableList<SalesWithDetailsAndProducts>) {
        items = itemsToAdd
        notifyDataSetChanged()
    }

}
class SalesChildItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.tvItemName
    val tvPrice = view.tvItemPrice
    val tvTime = view.tvTime
    val lvAddInfo = view.lvAddInfo
    val tvSalesCount = view.tvSalesCount
}