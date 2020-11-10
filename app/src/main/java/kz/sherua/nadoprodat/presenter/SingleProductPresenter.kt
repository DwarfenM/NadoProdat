package kz.sherua.nadoprodat.presenter

import android.content.Context
import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_item_dialog.*
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.model.dbentity.PropertyValues
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps
import kz.sherua.nadoprodat.state.SingleProductState
import kz.sherua.nadoprodat.view.SingleProductView

class SingleProductPresenter(val ctx: Context) :
    MviBasePresenter<SingleProductView, SingleProductState>() {
    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {

        val receivePropertiesIntent: Observable<SingleProductState> =
            intent(SingleProductView::receivePropertiesIntent).map {
                SingleProductState.PropertyReceived(it.map { prop ->
                    PropertyValuesWithProps(
                        property = prop,
                        propertyValues = null
                    )
                })
            }

        val productSaveIntent: Observable<SingleProductState> =
            intent(SingleProductView::saveProductIntent).flatMap { prod ->
                if (prod.isToCreate) {
                    npDb.productDao().insertProduct(
                        prod.product
                    ).toObservable().flatMap { id ->
                        for ((key, value) in prod.props) {
                            npDb.propertyValuesDao().insertPropertyValues(
                                PropertyValues(
                                    productId = id,
                                    propertyId = key.id!!,
                                    value = value
                                )
                            ).subscribe()
                        }
                        Observable.just(SingleProductState.ProductSaved)
                    }.subscribeOn(Schedulers.io())
                } else {
                    npDb.productDao().updateProduct(prod.product).doOnComplete {
                        npDb.propertyValuesDao().deletePropsByProductId(prod.product.id!!)
                            .doOnComplete {
                                for ((key, value) in prod.props) {
                                    npDb.propertyValuesDao().insertPropertyValues(
                                        PropertyValues(
                                            productId = prod.product.id!!,
                                            propertyId = key.id!!,
                                            value = value
                                        )
                                    ).subscribe()
                                }
                            }.subscribe()
                    }.toObservable<SingleProductState>()
                        .flatMap { Observable.just(SingleProductState.ProductSaved) }
                        .subscribeOn(Schedulers.io())
                }
            }

        val allIntents = Observable.merge(receivePropertiesIntent, productSaveIntent)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, SingleProductView::render)
    }
}