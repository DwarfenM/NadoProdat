package kz.sherua.nadoprodat.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.sherua.nadoprodat.state.AddPropDialogState

interface AddPropDialogView : MvpView {

    fun addPropIntent(): Observable<String>

    fun render(state:AddPropDialogState)
}