package com.example.tap_sample.fragments

import android.R
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tap_sample.adapters.SearchAdapter
import com.example.tap_sample.database.LineRoute
import com.example.tap_sample.databinding.FragmentRouteDialogBinding
import com.example.tap_sample.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRouteDialog :DialogFragment(),SearchView.OnSuggestionListener,SearchAdapter.OnItemClickListener  {
    private lateinit var binding: FragmentRouteDialogBinding
    private var mContext: Context? = null
    private var routesSearch: SearchView? = null
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(this) }


    companion object {
        val BUNDLE_OPERATOR = "operator"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRouteDialogBinding.inflate(inflater, container, false)

        dialog!!.window!!.setGravity(
            Gravity.CENTER_HORIZONTAL or Gravity.TOP
        )
        routesSearch = binding.routeSearch
        routesSearch!!.isIconifiedByDefault = false
        mContext = activity
        routesSearch!!.clearFocus()
        val searchManager = mContext!!
            .getSystemService(Context.SEARCH_SERVICE) as SearchManager
        routesSearch!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(requireActivity().componentName)
        )
        routesSearch!!.setOnSuggestionListener(this)
        routesSearch!!.queryHint = resources.getString(
            R.string.search_go
        )

        val id = routesSearch!!.context.resources
            .getIdentifier("android:id/search_src_text", null, null)
        val textView = routesSearch!!.findViewById<View>(id) as TextView
        textView.setTextColor(Color.WHITE)
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        )


      /*  routesSearch!!
            .setOnQueryTextFocusChangeListener { v, hasFocus -> // TODO Auto-generated method stub
                routesSearch!!.setQuery(" ", true)
                // open keyboard on hasFocus
                if (hasFocus) dialog!!
                    .getWindow()
                    ?.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                    )
                //loadData()
            }*/



        routesSearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchAdapter.filterData(query?: "")
                return true
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = searchAdapter

       /*loginViewModel.searchDatabase().observe(viewLifecycleOwner, Observer { list ->
            list?.let { searchAdapter.setData(it) }
        })*/

        loginViewModel.searchDatabase().distinctUntilChanged().observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                val uniqueList = it.distinctBy { lineRoute -> lineRoute.description }
                searchAdapter.setData(uniqueList)
            }
        })

        return binding.root
    }

    override fun onSuggestionSelect(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSuggestionClick(position: Int): Boolean {

        return true
    }

    override fun onItemClick(lineRoute: LineRoute) {

    //lineRoute.description?.let { loginViewModel.setSelectedValue(it) }

        val selectedValue = lineRoute.description

        if (selectedValue != null) {
            loginViewModel.setSelectedValue(selectedValue)
        }

        dismiss()
    }

}