package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps
import kz.sherua.nadoprodat.state.SingleProductState
import kz.sherua.nadoprodat.view.SingleProductView

class SingleProductPresenter (val ctx: Context) : MviBasePresenter<SingleProductView,SingleProductState>() {
    override fun bindIntents() {
        val receivePropertiesIntent : Observable<SingleProductState> =
            intent(SingleProductView::receivePropertiesIntent).map {
                SingleProductState.PropertyReceived(it.map { prop -> PropertyValuesWithProps(property = prop, propertyValues = null) })
            }

        val allIntents = receivePropertiesIntent

        subscribeViewState(allIntents, SingleProductView::render)
    }
}