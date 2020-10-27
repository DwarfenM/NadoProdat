package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.prop_name_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.Property

class PropertyNamesAdapter(val ctx: Context) : RecyclerView.Adapter<PropertyNamesViewHolder>(){

    private var data: MutableList<Property> = mutableListOf()
    private var dataToReturn: MutableList<Property> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyNamesViewHolder {
        return PropertyNamesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.prop_name_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PropertyNamesViewHolder, position: Int) {
        holder.tvPropName.text = data[position].name
        holder.lvHolder.setOnClickListener {
            if (holder.isChecked) {
                dataToReturn.remove(data[position])
                holder.ivChecked.visibility = View.GONE
                holder.isChecked = false
            } else {
                dataToReturn.add(data[position])
                holder.ivChecked.visibility = View.VISIBLE
                holder.isChecked = true
            }
        }
    }

    fun reassignItems(itemsToAdd: MutableList<Property>) {
        data = itemsToAdd
        notifyDataSetChanged()
    }

    fun getSelected(): List<Property>{
        return dataToReturn.toList()
    }
}

class PropertyNamesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val lvHolder = view.lvPropHolder
    val tvPropName = view.tvPropName
    val ivChecked = view.ivChecked
    var isChecked: Boolean = false
}