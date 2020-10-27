package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.state.PropertiesBottomDialogState

interface PropertiesBottomDialogView : MvpView {

    fun getAllPropsIntent(): Observable<Boolean>

    fun render(state: PropertiesBottomDialogState)
}