package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.state.SalesState
import kz.sherua.nadoprodat.view.SalesView

class SalesPresenter(val ctx: Context) : MviBasePresenter<SalesView, SalesState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {
        val checkSalesConditionIntent: Observable<SalesState> =
            intent(SalesView::checkSellsConditionIntent).flatMap {
                npDb.salesDao().getSales().toObservable().map {
                    if (it.isNotEmpty()) {
                        SalesState.GetAllSales(it)
                    } else {
                        SalesState.SalesEmpty
                    }
                }
            }

        val allIntents = checkSalesConditionIntent.observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, SalesView::render)
    }
}