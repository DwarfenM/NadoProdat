package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.StorageSortModel
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import kz.sherua.nadoprodat.state.StorageState

interface StorageView : MvpView {

    fun checkProductConditionIntent(): Observable<Boolean>

    fun searchProductsIntent(): Observable<String>

    fun sortStorage(): Observable<StorageSortModel>

    fun render(state: StorageState)
}