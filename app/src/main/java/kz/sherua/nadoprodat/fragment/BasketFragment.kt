package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.presenter.BasketPresenter
import kz.sherua.nadoprodat.state.BasketState
import kz.sherua.nadoprodat.view.BasketView

class BasketFragment : MviFragment<BasketView,BasketPresenter>(), BasketView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.textView2?.text = "Корзина"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_basket, container, false)

        return v
    }

    override fun createPresenter() = BasketPresenter()

    override fun openSearchIntent(): Observable<Boolean> {
        return RxView.clicks(btnSearch).map {
            true
        }
    }

    override fun render(state: BasketState) {
        when(state) {
            is BasketState.openSearch -> {
                activity?.searchView?.onActionViewExpanded()
                val closeBtn = androidx.appcompat.R.id.search_close_btn
                val imView = activity?.searchView?.findViewById<ImageView>(closeBtn)
                imView?.visibility = View.VISIBLE

                val searchFragment = SearchFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,searchFragment,"1")?.commit()
            }
        }
    }

}