package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.BasketModel
import kz.sherua.nadoprodat.state.AddItemState

interface AddItemView : MvpView {

    fun addCountIntent() : Observable<Int>

    fun removeCountIntent() : Observable<Int>

    fun addItemIntent() : Observable<BasketModel>

    fun render(state: AddItemState)
}