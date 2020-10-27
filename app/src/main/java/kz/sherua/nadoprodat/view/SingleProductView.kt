package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.state.SingleProductState

interface SingleProductView : MvpView {

    fun receivePropertiesIntent(): Observable<List<Property>>

    fun render(state: SingleProductState)
}