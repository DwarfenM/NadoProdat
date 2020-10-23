package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sales.*
import kotlinx.android.synthetic.main.fragment_sales.hasItemLayout
import kotlinx.android.synthetic.main.fragment_sales.searchConstraintLayout
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.BasketItemsAdapter
import kz.sherua.nadoprodat.adapter.SalesItemsAdapter
import kz.sherua.nadoprodat.model.DateRangeModel
import kz.sherua.nadoprodat.model.ParentSalesModel
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.presenter.SalesPresenter
import kz.sherua.nadoprodat.state.SalesState
import kz.sherua.nadoprodat.view.SalesView
import java.text.SimpleDateFormat
import java.util.*

class SalesFragment : MviFragment<SalesView, SalesPresenter>(), SalesView {

    private lateinit var itemsAdapter: SalesItemsAdapter
    private lateinit var dateRangeTrigger: BehaviorSubject<DateRangeModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateRangeTrigger = BehaviorSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.appBarMain?.visibility = View.VISIBLE
        activity?.tvHeaderTab?.visibility = View.VISIBLE
        activity?.tvHeaderTab?.text = "Продажи"
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

    override fun dateRangeSortIntent(): Observable<DateRangeModel> {
        return dateRangeTrigger
    }

    override fun render(state: SalesState) {
        when (state) {
            is SalesState.SalesEmpty -> {
                activity?.appBarMain?.visibility = View.VISIBLE
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
//                tvSalesSum.text = state.sellsList.sumByDouble { it.salesDetails.sumByDouble { it.salesDetails.productSalesPrice * it.salesDetails.productCount } }.toString()
                tvSalesSum.text = state.sellsList.sumByDouble { it.sales.salesPrice }.toString()
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
                tvSelectDatePeriod.setOnClickListener {
                    val builder = MaterialDatePicker.Builder.dateRangePicker()
                    val picker = builder.setTheme(R.style.MainDatePickerStyle).build()
                    picker.show(activity?.supportFragmentManager!!, picker.toString())
                    picker.addOnPositiveButtonClickListener {
                        dateRangeTrigger.onNext(DateRangeModel(it.first!!,it.second!!))
                        Log.d("tag","The selected date range is ${it.first} - ${it.second}")}
                }
                itemsAdapter.addItems(parentSalesModel.toMutableList());
            }
            is SalesState.DateRangeSales -> {
                activity?.appBarMain?.visibility = View.VISIBLE
                activity?.tvHeaderTab?.visibility = View.VISIBLE
                searchConstraintLayout.visibility = View.GONE
                emptySales.visibility = View.GONE
                hasItemLayout.visibility = View.VISIBLE
//                tvSalesSum.text = state.sellsList.sumByDouble { it.salesDetails.sumByDouble { it.salesDetails.productSalesPrice * it.salesDetails.productCount } }.toString()
                tvSalesSum.text = state.sellsList.sumByDouble { it.sales.salesPrice }.toString()
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
                tvSelectDatePeriod.setOnClickListener {
                    val builder = MaterialDatePicker.Builder.dateRangePicker()
                    val picker = builder.setTheme(R.style.MainDatePickerStyle).build()
                    picker.show(activity?.supportFragmentManager!!, picker.toString())
                    picker.addOnPositiveButtonClickListener {
                        dateRangeTrigger.onNext(DateRangeModel(it.first!!,it.second!!))
                        Log.d("tag","The selected date range is ${it.first} - ${it.second}")}
                }
                itemsAdapter.addItems(parentSalesModel.toMutableList());

            }
        }
    }
}