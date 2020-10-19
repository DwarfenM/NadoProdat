package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.SaleDetails
import kz.sherua.nadoprodat.model.dbentity.Sales
import kz.sherua.nadoprodat.state.BasketState
import kz.sherua.nadoprodat.utils.PreferenceHelper
import kz.sherua.nadoprodat.utils.PreferenceHelper.getBasket
import kz.sherua.nadoprodat.utils.PreferenceHelper.setBasket
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
                val basket = getBasket(ctx)
                if (basket.size > 0) {
                    BasketState.ItemAdded(getBasket(ctx))
                } else {
                    BasketState.CloseSearch
                }
            }

        val sortIntent: Observable<BasketState> =
            intent(BasketView::sortIntent).map {asc ->
                val basket = getBasket(ctx)
                if (asc) {
                    basket.sortBy { item -> item.salesPrice }
                } else {
                    basket.sortByDescending { item -> item.salesPrice }
                }
                BasketState.SortedState(basket)
            }

        val deleteBasketIntent: Observable<BasketState> =
            intent(BasketView::deleteBasketIntent).map {
                val basket: List<Product> = arrayListOf()
                setBasket(basket.toMutableList(),ctx)
                BasketState.BasketDeletedState(arrayListOf())
            }

        val addItemFromSearchIntent: Observable<BasketState> =
            intent(BasketView::addItemIntent).map {
                val product = it.product
                product.count = 1
                PreferenceHelper.addItemToSP(product, ctx)
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
                            productCount = it.count,
                            productSalesPrice = it.salesPrice * it.count,
                            crDate = System.currentTimeMillis()
                        )
                    }
                    ).subscribe()

                }.subscribeOn(Schedulers.io()).subscribe()
                setBasket(arrayListOf(), ctx)
                BasketState.ItemsSelled
            }

        val findItemIntent: Observable<BasketState> =
            intent(BasketView::searchItemIntent).flatMap { nameLike ->
                if (nameLike.isNotEmpty() && nameLike.isNotBlank()){
                npDb.productDao().getFoundProducts(nameLike).map {
                    BasketState.FoundItem(it)
                }.subscribeOn(Schedulers.io()).toObservable()
                } else {
                    Observable.just(BasketState.FoundItem(arrayListOf()))
                }
            }

        val allIntents = Observable.merge(Observable.merge(openSearchIntent,addItemFromSearchIntent), closeSearchIntent,itemAddedIntent, Observable.merge(itemSellIntent,sortIntent,deleteBasketIntent,findItemIntent)).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, BasketView::render)
    }
}