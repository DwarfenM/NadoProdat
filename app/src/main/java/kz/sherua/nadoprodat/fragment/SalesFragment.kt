package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sales.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.presenter.SalesPresenter
import kz.sherua.nadoprodat.state.SalesState
import kz.sherua.nadoprodat.view.SalesView

class SalesFragment : MviFragment<SalesView, SalesPresenter>(), SalesView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun createPresenter() = SalesPresenter(activity!!)

    override fun checkSellsConditionIntent(): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun render(state: SalesState) {
        when(state) {
            is SalesState.SalesEmpty -> {
                activity?.appBarMain?.visibility = View.VISIBLE
                appBarBasket.visibility = View.GONE
                activity?.tvHeaderTab?.visibility = View.VISIBLE
                searchConstraintLayout.visibility = View.GONE
                emptySales.visibility = View.VISIBLE
                hasItemLayout.visibility = View.GONE
            }
            is SalesState.GetAllSales -> {

            }
        }
    }

}