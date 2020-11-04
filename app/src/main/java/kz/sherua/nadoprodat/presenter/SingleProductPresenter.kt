package kz.sherua.nadoprodat.presenter

import android.content.Context
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
                        Product(
                            name = prod.productName,
                            count = prod.productCount.toInt(),
                            salesPrice = prod.productSalesPrice,
                            costPrice = prod.productCostPrice,
                            crDate = System.currentTimeMillis()
                        )
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
                    npDb.propertyValuesDao().deletePropsByProductId(prod.productId!!).doOnComplete{
                        for ((key, value) in prod.props) {
                            npDb.propertyValuesDao().insertPropertyValues(
                                PropertyValues(
                                    productId = prod.productId!!,
                                    propertyId = key.id!!,
                                    value = value
                                )
                            ).subscribe()
                        }
                    }.toObservable<SingleProductState>().flatMap {
                        Observable.just(SingleProductState.ProductSaved)
                    }.subscribeOn(Schedulers.io())
                }
            }

        val allIntents = Observable.merge(receivePropertiesIntent, productSaveIntent)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, SingleProductView::render)
    }
}