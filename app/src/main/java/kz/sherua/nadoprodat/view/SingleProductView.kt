package kz.sherua.nadoprodat.view

import android.net.Uri
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.ProductToSave
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.state.SingleProductState

interface SingleProductView : MvpView {

    fun receivePropertiesIntent(): Observable<List<Property>>

    fun saveProductIntent(): Observable<ProductToSave>

    fun getImagePathIntent(): Observable<Uri>

    fun render(state: SingleProductState)
}