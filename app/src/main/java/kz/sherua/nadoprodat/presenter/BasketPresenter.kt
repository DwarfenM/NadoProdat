package kz.sherua.nadoprodat.presenter

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.sherua.nadoprodat.state.BasketState
import kz.sherua.nadoprodat.view.BasketView

class BasketPresenter : MviBasePresenter<BasketView, BasketState>() {

    override fun bindIntents() {
        val openSearchIntent: Observable<BasketState> =
            intent(BasketView::openSearchIntent).map {
                BasketState.openSearch
            }

        val allIntents = openSearchIntent.observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, BasketView::render)
    }
}