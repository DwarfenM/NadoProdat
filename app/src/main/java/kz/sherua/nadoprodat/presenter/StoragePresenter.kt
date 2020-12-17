package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.state.StorageState
import kz.sherua.nadoprodat.view.StorageView

class StoragePresenter(ctx: Context) : MviBasePresenter<StorageView, StorageState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {
        val checkSalesConditionIntent: Observable<StorageState> =
            intent(StorageView::checkProductConditionIntent).flatMap {
                npDb.productDao().getAllProducts().toObservable().map {
                    if (it.isNotEmpty()) {
                        StorageState.GetAllProducts(it)
                    } else {
                        StorageState.NoProducts
                    }
                }.subscribeOn(Schedulers.io())
            }

        val searchProductsIntent: Observable<StorageState> =
            intent(StorageView::searchProductsIntent).flatMap { productName ->
                npDb.productDao().getFoundProducts(productName).toObservable().map {
                    if (it.isNotEmpty()) {
                        StorageState.GetAllProducts(it)
                    } else {
                        StorageState.NoProducts
                    }
                }.subscribeOn(Schedulers.io())
            }

        val sortStorageState: Observable<StorageState> =
            intent(StorageView::sortStorage).map { sortModel ->
                when (sortModel.sortType) {
                    "По дате добавления" -> {
                        StorageState.GetAllProducts(sortModel.products.sortedByDescending { it.product.crDate }.toList())
                    }
                    "По цене за товар" -> {
                        StorageState.GetAllProducts(sortModel.products.sortedByDescending { it.product.costPrice }.toList())
                    }
                    "По алфавиту" -> {
                        StorageState.GetAllProducts(sortModel.products.sortedByDescending { it.product.name }.toList())
                    }
                    else -> {
                        StorageState.GetAllProducts(sortModel.products)
                    }
                }

            }

        val allIntents =Observable.merge(searchProductsIntent,checkSalesConditionIntent, sortStorageState).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, StorageView::render)
    }
}