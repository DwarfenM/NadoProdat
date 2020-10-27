package kz.sherua.nadoprodat.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.property_item.view.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps

class PropertiesAdapter(val ctx: Context) : RecyclerView.Adapter<PropertiesViewHolder>() {

    private var data: MutableList<PropertyValuesWithProps> = mutableListOf()
    private var viewHolders: MutableList<PropertiesViewHolder> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        return PropertiesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.property_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.tvPropertyLabel.text = "Свойство " + data[position].property.id
        holder.tvPropertyName.text = data[position].property.name
        holder.etPropValue.setText(data[position].propertyValues?.value)
        holder.lvDelete.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
            Log.d("Current data: ", getItems().toString())
        }
        viewHolders.add(holder)
    }

    fun reassignItems(itemsToAdd: MutableList<PropertyValuesWithProps>) {
        data = itemsToAdd
        notifyDataSetChanged()
    }

    fun addItems(itemsToAdd: List<PropertyValuesWithProps>) {
        data.addAll(itemsToAdd)
        notifyDataSetChanged()
    }

    fun getItems(): HashMap<Property, String> {
        val mapOfProps = HashMap<Property, String>()
        data.forEach {
            mapOfProps[it.property] = viewHolders.first{hold -> hold.tvPropertyName.text == it.property.name}.etPropValue.text.toString()
        }
        return mapOfProps
    }

}

class PropertiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvPropertyLabel = view.tvPropertyLabel
    val tvPropertyName = view.tvPropertyName
    val etPropValue = view.etPropValue
    val lvDelete = view.lvDelete
}