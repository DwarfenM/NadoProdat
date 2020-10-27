package kz.sherua.nadoprodat.dialog

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hannesdorfmann.mosby3.mvi.MviDialogFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BaseBottomDialogMVI<V: MvpView, S:Any, P: MviPresenter<V, S>>: MviDialogFragment<V,P> (){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(this.context!!, this.theme)
    }

}