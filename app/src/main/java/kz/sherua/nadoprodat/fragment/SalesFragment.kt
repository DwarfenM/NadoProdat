package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sales.*
import kotlinx.android.synthetic.main.fragment_sales.hasItemLayout
import kotlinx.android.synthetic.main.fragment_sales.searchConstraintLayout
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.BasketItemsAdapter
import kz.sherua.nadoprodat.adapter.SalesItemsAdapter
import kz.sherua.nadoprodat.model.ParentSalesModel
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.presenter.SalesPresenter
import kz.sherua.nadoprodat.state.SalesState
import kz.sherua.nadoprodat.view.SalesView
import java.text.SimpleDateFormat
import java.util.*

class SalesFragment : MviFragment<SalesView, SalesPresenter>(), SalesView {

    private lateinit var itemsAdapter: SalesItemsAdapter

    val NAME = "SalesFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }


    override fun onResume() {
        super.onResume()
        Log.d("resume", "resume")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.appBarMain?.visibility = View.VISIBLE
        appBarSales.visibility = View.GONE
        activity?.tvHeaderTab?.visibility = View.VISIBLE
        searchConstraintLayout.visibility = View.GONE
        emptySales.visibility = View.VISIBLE
        hasItemLayout.visibility = View.GONE

        itemsAdapter = SalesItemsAdapter(context!!)
        rvSales.adapter = itemsAdapter
        rvSales.layoutManager = GridLayoutManager(context!!, 1)
    }

    override fun createPresenter() = SalesPresenter(activity!!)

    override fun checkSellsConditionIntent(): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun render(state: SalesState) {
        when (state) {
            is SalesState.SalesEmpty -> {
                activity?.appBarMain?.visibility = View.VISIBLE
                appBarSales.visibility = View.GONE
                activity?.tvHeaderTab?.visibility = View.VISIBLE
                searchConstraintLayout.visibility = View.GONE
                emptySales.visibility = View.VISIBLE
                hasItemLayout.visibility = View.GONE
            }
            is SalesState.GetAllSales -> {
                activity?.appBarMain?.visibility = View.VISIBLE
                activity?.tvHeaderTab?.visibility = View.VISIBLE
                searchConstraintLayout.visibility = View.GONE
                emptySales.visibility = View.GONE
                hasItemLayout.visibility = View.VISIBLE
                val groupedList = state.sellsList.groupBy {
                    val date = Date(it.sales.crDate)
                    val formatter = SimpleDateFormat("dd/MM/yyyy")
                    formatter.parse(formatter.format(date))
                }
                val parentSalesModel = groupedList.toList().map {
                    ParentSalesModel(
                        it.first,
                        it.second.map { sales ->
                            sales.sales.salesPrice
                        }.sum(),
                        it.second.toMutableList()
                    )
                }
                itemsAdapter.addItems(parentSalesModel.toMutableList());
//                Log.d("Date list: ", parentSalesModel.toString())
//                listOfDate.forEach {
//                    Log.d("Date: ", it.toString())
//                }
//                val list : MutableList<ParentSalesModel> = state.sellsList.map {
//                    ParentSalesModel(date = )
//                }
            }
        }
    }

}