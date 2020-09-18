package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.model.dbentity.SaleDetails
import kz.sherua.nadoprodat.model.dbentity.Sales
import kz.sherua.nadoprodat.state.BasketState
import kz.sherua.nadoprodat.utils.PreferenceHelper.getBasket
import kz.sherua.nadoprodat.view.BasketView

class BasketPresenter(val ctx: Context) : MviBasePresenter<BasketView, BasketState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {
        val openSearchIntent: Observable<BasketState> =
            intent(BasketView::openSearchIntent).map {
                BasketState.OpenSearch
            }

        val closeSearchIntent: Observable<BasketState> =
            intent(BasketView::closeSearchIntent).map {
                BasketState.CloseSearch
            }

        val itemAddedIntent: Observable<BasketState> =
            intent(BasketView::itemAddedIntent).map {
                BasketState.ItemAdded(getBasket(ctx))
            }

        val itemSellIntent: Observable<BasketState> =
            intent(BasketView::performSellIntent).map { products ->
                val sale = Sales(
                    salesPrice = products.map { it.salesPrice * it.count }.sum(),
                    crDate = System.currentTimeMillis()
                )
                npDb.salesDao().insertSale(sale).map { id ->
                    products.forEach{
                        npDb.productDao().updateProduct(it.id!!,it.count).subscribe()
                    }
                    npDb.saleDetailsDao().insertAllSaleDetails(products.map {
                        SaleDetails(
                            salesId = id,
                            productId = it.id!!,
                            productSalesPrice = it.salesPrice,
                            crDate = System.currentTimeMillis()
                        )
                    }
                    ).subscribe()

                }.subscribeOn(Schedulers.io()).subscribe()
                BasketState.ItemsSelled
            }

        val allIntents = Observable.merge(openSearchIntent, closeSearchIntent,itemAddedIntent, itemSellIntent).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, BasketView::render)
    }
}