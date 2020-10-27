package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.state.PropertiesBottomDialogState
import kz.sherua.nadoprodat.view.PropertiesBottomDialogView

class PropertiesBottomDialogPresenter(val ctx: Context) : MviBasePresenter<PropertiesBottomDialogView,PropertiesBottomDialogState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {

        val getAllPropNames: Observable<PropertiesBottomDialogState> =
            intent(PropertiesBottomDialogView::getAllPropsIntent).flatMap {
                npDb.propertyDao().getAllProps().map {
                    PropertiesBottomDialogState.GetAllPropNames(it)
                }.subscribeOn(Schedulers.io()).toObservable()
            }

        val allIntents = getAllPropNames.observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, PropertiesBottomDialogView::render)

    }
}