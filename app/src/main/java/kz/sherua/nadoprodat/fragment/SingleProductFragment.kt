package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_single_product.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.PropertiesAdapter
import kz.sherua.nadoprodat.dialog.AddItemDialog
import kz.sherua.nadoprodat.dialog.AddPropDialog
import kz.sherua.nadoprodat.dialog.PropertiesBottomDialogFragment
import kz.sherua.nadoprodat.model.ProductToSave
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.presenter.SingleProductPresenter
import kz.sherua.nadoprodat.state.SingleProductState
import kz.sherua.nadoprodat.view.SingleProductView
import java.text.SimpleDateFormat
import java.util.*

class SingleProductFragment : MviFragment<SingleProductView, SingleProductPresenter>(), SingleProductView {

    private lateinit var itemsAdapter: PropertiesAdapter
    private lateinit var itemAdded: BehaviorSubject<List<Property>>
    private lateinit var product: ProductWithProps
    private val args: SingleProductFragmentArgs by navArgs()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemAdded = BehaviorSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lvAddProp.setOnClickListener {
//            val navController =
//                Navigation.findNavController((context as FragmentActivity).findViewById(R.id.nav_host_fragment))
//            navController.navigate(R.id.propertiesBottomDialogFragment)
            val dialog = PropertiesBottomDialogFragment(itemAdded)
            dialog.show(activity?.supportFragmentManager!!, PropertiesBottomDialogFragment.TAG)
            activity?.supportFragmentManager!!.executePendingTransactions()
        }
        itemsAdapter = PropertiesAdapter(context!!)
        rvProps.adapter = itemsAdapter
        rvProps.layoutManager = GridLayoutManager(context!!, 1)
        if (!args.isNew) {
            product = Gson().fromJson(args.productData, ProductWithProps::class.java)
            etProductName.setText(product.product.name)
            tvProductCount.text = product.product.count.toString()
            etSellPrice.setText(product.product.salesPrice.toString())
            etActualPrice.setText(product.product.costPrice.toString())
            tvAddedDate.text = dateFormat.format(Date(product.product.crDate!!))
            if (product.propertyValues.isNotEmpty()) {
                itemsAdapter.reassignItems(product.propertyValues.toMutableList())
            }
        }
    }

    override fun createPresenter() = SingleProductPresenter(context!!)

    override fun receivePropertiesIntent(): Observable<List<Property>> {
        return itemAdded
    }

    override fun saveProductIntent(): Observable<ProductToSave> {
        return RxView.clicks(btnAddItem).map {
            ProductToSave(product?.product.id,etProductName.text.toString(),tvProductCount.text.toString().toInt(),etSellPrice.text.toString().toDouble(), etActualPrice.text.toString().toDouble(),itemsAdapter.getItems(),args.isNew)
        }
    }

    override fun render(state: SingleProductState) {
        when(state) {
            is SingleProductState.PropertyReceived -> {
                Log.d("tag", state.property.toString())
                itemsAdapter.addItems(state.property)
            }
            is SingleProductState.ProductSaved -> {
                activity?.onBackPressed()
            }
        }
    }

}