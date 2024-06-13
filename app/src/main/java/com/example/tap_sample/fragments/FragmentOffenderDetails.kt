package com.example.tap_sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tap_sample.MainActivity
import com.example.tap_sample.R
import com.example.tap_sample.databinding.FragmentOffenderDetailsBinding


class FragmentOffenderDetails : Fragment() {
    private lateinit var binding: FragmentOffenderDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
         binding = FragmentOffenderDetailsBinding.inflate(inflater,container,false)

        val arguments = arguments
        if (arguments != null) {
            val dob = arguments.getString("dob")
            binding.txtDateofbirth.text = dob
            val violationCode = arguments.getString("violation_code")
            binding.txtViolationCode.text = violationCode
            val violationDescription  = arguments.getString("violation_description")
            binding.txtViolationDescription.text = violationDescription
            val serviceCategory = arguments.getString("service_category")
            binding.txtServiceCategory.text = serviceCategory
            val exclusionPeriod = arguments.getString("exclusion_period")
            binding.txtExclusionPeriod.text = exclusionPeriod
            val effectiveDate = arguments.getString("effective_date")
            binding.txtEffectiveDate.text = effectiveDate
            val tapCard = arguments.getString("tap_card")
            binding.txtTapcard.text = tapCard
        }

        return binding.root

    }

    override fun onResume() {
        (requireContext() as MainActivity).setToolbarTitle(getString(R.string.action_offender))
        super.onResume()
    }


}