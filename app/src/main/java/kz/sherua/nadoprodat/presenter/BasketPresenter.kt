package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.sherua.nadoprodat.state.BasketState
import kz.sherua.nadoprodat.utils.PreferenceHelper.getBasket
import kz.sherua.nadoprodat.view.BasketView

class BasketPresenter(val ctx: Context) : MviBasePresenter<BasketView, BasketState>() {

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

        val allIntents = Observable.merge(openSearchIntent, closeSearchIntent,itemAddedIntent).observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, BasketView::render)
    }
}