package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.BasketItemsAdapter
import kz.sherua.nadoprodat.adapter.SearchItemsAdapter
import kz.sherua.nadoprodat.dialog.AddItemDialog
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import kz.sherua.nadoprodat.presenter.BasketPresenter
import kz.sherua.nadoprodat.state.BasketState
import kz.sherua.nadoprodat.view.BasketView

class BasketFragment : MviFragment<BasketView,BasketPresenter>(), BasketView{
    private lateinit var itemsAdapter: BasketItemsAdapter
    private lateinit var closeSearch: BehaviorSubject<Boolean>
    private lateinit var openSearch: BehaviorSubject<Boolean>
    private lateinit var itemAdded: BehaviorSubject<Boolean>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var searchAdapter: SearchItemsAdapter
    private lateinit var addItemTrigger: BehaviorSubject<ProductWithProps>
    private val sortVals = arrayListOf("Сортировка", "По возрастанию цены", "По убыванию цены")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        closeSearch = BehaviorSubject.create()
        itemAdded = BehaviorSubject.create()
        openSearch = BehaviorSubject.create()
        addItemTrigger = BehaviorSubject.create()
    }

    override fun onResume() {
        super.onResume()
        appBarBasket.visibility = View.GONE
        itemAdded.onNext(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ArrayAdapter(context!!, R.layout.simple_spinner, sortVals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSort.adapter = adapter
        activity?.tvHeaderTab?.text = "Корзина"
        activity?.searchView?.setOnSearchClickListener {
            activity?.searchView?.onActionViewCollapsed()
            openSearch.onNext(true)
        }
        toolbarBasket.setNavigationIcon(R.drawable.ic_back_button)
        toolbarBasket.setNavigationOnClickListener{
            closeSearch.onNext(true)
        }
        itemsAdapter = BasketItemsAdapter(context!!, btnSell)
        rvBasket.adapter = itemsAdapter
        rvBasket.layoutManager = GridLayoutManager(context!!,1)
        searchAdapter = SearchItemsAdapter(context!!,addItemTrigger)
        rvSearchItems.adapter = searchAdapter
        rvSearchItems.layoutManager = GridLayoutManager(context!!, 1)
        activity?.searchView?.setOnQueryTextFocusChangeListener{ v, hasFocus ->
            if(!hasFocus) {
//                closeSearch.onNext(true)
            }
        }

        btnAddItem.setOnClickListener{
            val dialog = AddItemDialog(itemAdded)
            dialog.show(activity?.supportFragmentManager!!, AddItemDialog.TAG)
            activity?.supportFragmentManager!!.executePendingTransactions()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun createPresenter() = BasketPresenter(context!!)

    override fun openSearchIntent(): Observable<Boolean> {
            return Observable.merge(RxView.clicks(btnSearch).map {
                true
            },openSearch)
    }

    override fun closeSearchIntent(): Observable<Boolean> {
        return closeSearch
    }

    override fun itemAddedIntent(): Observable<Boolean> {
        return itemAdded
    }

    override fun performSellIntent(): Observable<List<Product>> {
        return RxView.clicks(btnSell).map {
            itemsAdapter.getProducts()
        }
    }

    override fun addItemIntent(): Observable<ProductWithProps> {
        return addItemTrigger
    }

    override fun searchItemIntent(): Observable<String> {
        return RxSearchView.queryTextChanges(searchViewBasket)
            .map (CharSequence::toString)
    }

    override fun sortIntent(): Observable<Boolean> {
        return RxAdapterView.itemSelections(spinnerSort).map {
            when (it) {
                1 -> {
                    true
                }
                2 -> {
                    false
                }
                else -> false
            }
        }
    }

    override fun deleteBasketIntent(): Observable<Boolean> {
        return RxView.clicks(btnDeleteAllFromBasket).map {
            true
        }
    }

    override fun render(state: BasketState) {
        when(state) {
            is BasketState.OpenSearch -> {
//                activity?.textView2?.visibility = View.GONE
                val closeBtn = androidx.appcompat.R.id.search_close_btn
                val imView = activity?.searchView?.findViewById<ImageView>(closeBtn)
                imView?.visibility = View.VISIBLE
                activity?.appBarMain?.visibility = View.GONE
                appBarBasket.visibility = View.VISIBLE
                searchViewBasket.onActionViewExpanded()
                searchConstraintLayout.visibility = View.VISIBLE
                emptyBasket.visibility = View.GONE
                hasItemLayout.visibility = View.GONE
            }
            is BasketState.CloseSearch -> {
                activity?.appBarMain?.visibility = View.VISIBLE
                appBarBasket.visibility = View.GONE
                activity?.tvHeaderTab?.visibility = View.VISIBLE
                searchConstraintLayout.visibility = View.GONE
                emptyBasket.visibility = View.VISIBLE
                hasItemLayout.visibility = View.GONE
            }
            is BasketState.ItemAdded -> {
                searchConstraintLayout.visibility = View.GONE
                activity?.appBarMain?.visibility = View.VISIBLE
                appBarBasket.visibility = View.GONE
                emptyBasket.visibility = View.GONE
                searchViewBasket.onActionViewCollapsed()
                hasItemLayout.visibility = View.VISIBLE
                itemsAdapter.addItems(state.basketList)
                btnSell.text = "Заработать " + state.basketList.map { it.salesPrice * it.count }.sum()
            }
            is BasketState.ItemsSelled -> {
                itemAdded.onNext(true)
            }
            is BasketState.SortedState -> {
                itemsAdapter.addItems(state.basketList)
            }
            is BasketState.BasketDeletedState -> {
                itemsAdapter.addItems(state.basketList)
                itemAdded.onNext(true)
            }
            is BasketState.FoundItem -> {
                searchAdapter.addItems(state.foundItems)
            }
        }
    }



}