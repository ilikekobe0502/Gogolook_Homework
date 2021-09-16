package com.gogolook.homework.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogolook.homework.R
import com.gogolook.homework.enums.DisplayMode
import com.gogolook.homework.model.api.ApiResult
import com.gogolook.homework.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_contacts.*

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var searchAdapter: SearchAdapter = SearchAdapter()
    private var searchHistoryAdapter: SearchHistoryAdapter = SearchHistoryAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_contacts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        setupUi()
    }

    /**
     * set observe listener
     */
    private fun setObserve() {
        viewModel.searchHistoryResult.observe(viewLifecycleOwner, {
            searchHistoryAdapter.updateData(it)
        })

        viewModel.imagesResult.observe(viewLifecycleOwner, {
            when (it) {
                is ApiResult.Loading -> {
                    if (searchAdapter.data.isEmpty()) {
                        progress.visibility = View.VISIBLE
                    } else {
                        progress_more.visibility = View.VISIBLE
                    }
                }
                is ApiResult.Loaded -> progress.visibility = View.GONE
                is ApiResult.Success -> {
                    if (progress.isVisible)
                        progress.visibility = View.GONE
                    if (progress_more.isVisible)
                        progress_more.visibility = View.GONE
                    it.result?.let { data -> searchAdapter.updateData(data, viewModel.page) }

                }
                is Error -> {

                }
            }
        })
        viewModel.displayModeResult.observe(viewLifecycleOwner, {
            when (it) {
                DisplayMode.GRID -> {
                    iv_display_type.setBackgroundResource(R.drawable.ic_baseline_grid_on_24)
                    setRecyclerViewMode(GridLayoutManager(requireContext(), 4))
                }
                DisplayMode.LIST -> {
                    iv_display_type.setBackgroundResource(R.drawable.ic_baseline_grid_off_24)
                    setRecyclerViewMode(
                        LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    )
                }
            }
        })

    }


    private fun setupUi() {
        viewModel.getDisplayMode()
        viewModel.getImages("")

        rv_contacts.adapter = searchAdapter

        rv_history.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchHistoryAdapter
        }

        searchHistoryAdapter.setOnItemClickListener { v ->
            et_search.setText((v?.tag as String))
            viewModel.getImages(et_search.text.toString(), true)
            rv_history.visibility = View.GONE
        }

        et_search.setOnClickListener {
            rv_history.visibility = View.VISIBLE
        }

        iv_display_type.setOnClickListener {
            viewModel.changeDisplayMode()
        }

        iv_search.setOnClickListener {
            viewModel.getImages(et_search.text.toString(), true)
            rv_history.visibility = View.GONE

        }
    }

    private fun setRecyclerViewMode(manager: RecyclerView.LayoutManager) {
        rv_contacts.layoutManager = manager

        rv_contacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.layoutManager is LinearLayoutManager) {
                    if ((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == searchAdapter.data.size - 1 && !progress_more.isVisible) {
                        //bottom of list!
                        viewModel.getImages(et_search.text.toString())
                    }
                } else if (recyclerView.layoutManager is GridLayoutManager) {
                    if ((recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition() == searchAdapter.data.size - 1 && !progress_more.isVisible) {
                        //bottom of list!
                        viewModel.getImages(et_search.text.toString())
                    }
                }
            }
        })

    }
}