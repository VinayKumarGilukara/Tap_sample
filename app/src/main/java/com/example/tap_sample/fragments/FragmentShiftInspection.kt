package com.example.tap_sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tap_sample.MainActivity
import com.example.tap_sample.R


class FragmentShiftInspection : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_shift_inspection, container, false)
    }

    override fun onResume() {
        (requireContext() as MainActivity).setToolbarTitle(getString(R.string.action_inspect))
        super.onResume()
    }


}