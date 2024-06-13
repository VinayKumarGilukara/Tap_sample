package com.example.tap_sample.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tap_sample.MainActivity
import com.example.tap_sample.R
import com.example.tap_sample.adapters.HotSheetAdapter
import com.example.tap_sample.database.HotSheet
import com.example.tap_sample.databinding.FragmentHotListBinding

import com.example.tap_sample.viewmodels.LoginViewModel


class FragmentHotList : Fragment(),HotSheetAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHotListBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val hotSheetAdapter: HotSheetAdapter by lazy { HotSheetAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHotListBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = hotSheetAdapter

        loginViewModel.allTopSheets.observe(viewLifecycleOwner, Observer { topSheets ->
            topSheets?.let { hotSheetAdapter.setData(it)
            }
        })

        binding.listsearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Not used
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                hotSheetAdapter.filterData(s.toString())
            }
        })

        return binding.root
    }

    override fun onItemClick(hotSheet: HotSheet) {
        val transaction = requireFragmentManager().beginTransaction()
        val newFragment: Fragment = FragmentOffenderDetails()

        val bundle = Bundle()
        bundle.putString("dob", hotSheet.dob)
        bundle.putString("violation_code",hotSheet.violationCode)
        bundle.putString("violation_description",hotSheet.violationDescription)
        bundle.putString("service_category", hotSheet.serviceCategory)
        bundle.putString("exclusion_period", hotSheet.exclusionPeriod)
        bundle.putString("effective_date", hotSheet.comment)
        bundle.putString("tap_card", hotSheet.tapCard)

        newFragment.arguments = bundle

        transaction.replace(R.id.panel_layout, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    override fun onResume() {
        (requireContext() as MainActivity).setToolbarTitle(getString(R.string.action_offender))
        super.onResume()
    }


}