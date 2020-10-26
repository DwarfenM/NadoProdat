package kz.sherua.nadoprodat.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_single_sale.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.SingleSaleItemsAdapter
import kz.sherua.nadoprodat.model.SingleSaleItem
import java.text.SimpleDateFormat

class SingleSaleFragment : Fragment() {

    lateinit var listOfSaleItems: SingleSaleItem
    lateinit var adapter: SingleSaleItemsAdapter
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
    val args: SingleSaleFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val listString = arguments?.getString(LIST_OF_SALE_DETAILS)
        listOfSaleItems = Gson().fromJson(args.listOfSaleDetails,SingleSaleItem::class.java)
        adapter = SingleSaleItemsAdapter(context!!)
        activity?.appBarMain?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_sale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity?.window
            window!!.statusBarColor = ContextCompat.getColor(activity!!,R.color.lightGreen)
        }
        toolbarSingleSale.navigationIcon = resources.getDrawable(R.drawable.ic_back_button)
        toolbarSingleSale.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        tvItemsSum.text = listOfSaleItems.listOfItems.sumByDouble { it.salesDetails.productSalesPrice }.toString()
        tvDate.text = dateFormat.format(listOfSaleItems.date)
        tvItemsCount.text = listOfSaleItems.listOfItems.count().toString() + " товаров"
        rvSingleSaleItems.adapter = adapter
        rvSingleSaleItems.layoutManager = GridLayoutManager(context!!, 1)
        adapter.addItems(listOfSaleItems.listOfItems.toMutableList())
    }
}