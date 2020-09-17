package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.state.BasketState

interface BasketView : MvpView {

    fun openSearchIntent(): Observable<Boolean>

    fun closeSearchIntent(): Observable<Boolean>

    fun itemAddedIntent(): Observable<Boolean>

    fun render(state: BasketState)
}