package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.state.BasketState

interface BasketView : MvpView {

    fun openSearchIntent(): Observable<Boolean>

    fun closeSearchIntent(): Observable<Boolean>

    fun itemAddedIntent(): Observable<Boolean>

    fun performSellIntent(): Observable<List<Product>>

//    fun addItemIntent(): Observable<ProductWithProperty>

    fun searchItemIntent(): Observable<String>

    fun sortIntent(): Observable<Boolean>

    fun deleteBasket(): Observable<Boolean>

    fun render(state: BasketState)
}