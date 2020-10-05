package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.sherua.nadoprodat.state.SalesState
import kz.sherua.nadoprodat.view.SalesView

class SalesPresenter(val ctx: Context) : MviBasePresenter<SalesView, SalesState>() {

    override fun bindIntents() {
//        val checkSalesConditionIntent: Observable<SalesState> =
//            intent(SalesView::checkSellsConditionIntent).map {
//
//            }
    }
}