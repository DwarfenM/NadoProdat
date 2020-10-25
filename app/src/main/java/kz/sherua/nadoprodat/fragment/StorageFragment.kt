package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_storage.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.SalesItemsAdapter
import kz.sherua.nadoprodat.adapter.StorageItemsAdapter
import kz.sherua.nadoprodat.presenter.StoragePresenter
import kz.sherua.nadoprodat.state.StorageState
import kz.sherua.nadoprodat.view.StorageView

class StorageFragment : MviFragment<StorageView, StoragePresenter>(), StorageView {

    private lateinit var itemsAdapter: StorageItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsAdapter = StorageItemsAdapter(context!!)
        rvStorage.adapter = itemsAdapter
        rvStorage.layoutManager = GridLayoutManager(context!!, 1)
    }

    override fun createPresenter() = StoragePresenter(context!!)

    override fun checkProductConditionIntent(): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun render(state: StorageState) {
        when(state) {
            is StorageState.NoProducts -> {
                emptyStorage.visibility = View.VISIBLE
                searchConstraintLayout.visibility = View.GONE
                hasItemLayout.visibility = View.GONE
            }
            is StorageState.GetAllProducts -> {
                emptyStorage.visibility = View.GONE
                searchConstraintLayout.visibility = View.GONE
                hasItemLayout.visibility = View.VISIBLE
                itemsAdapter.addItems(state.productList.toMutableList())
            }
        }
    }
}