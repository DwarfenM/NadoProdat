package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.DateRangeModel
import kz.sherua.nadoprodat.state.SalesState

interface SalesView : MvpView {

    fun checkSellsConditionIntent(): Observable<Boolean>

    fun dateRangeSortIntent(): Observable<DateRangeModel>

    fun render(state: SalesState)

}