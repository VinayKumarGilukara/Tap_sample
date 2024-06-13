package com.example.tap_sample.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tap_sample.MainActivity
import com.example.tap_sample.R
import com.example.tap_sample.database.Inspector
import com.example.tap_sample.databinding.FragmentDiagnosticBinding
import com.example.tap_sample.preferences.TapPreferences


class FragmentDiagnostic : Fragment(){
    private lateinit var binding: FragmentDiagnosticBinding
    private var pref: TapPreferences? = null
    private var mContext: Context? = null
    private lateinit var mLoginUser:TextView
    private lateinit var mLoginId:TextView
    private lateinit var mVersion:TextView
    private lateinit var mCommunicationTime:TextView
    private lateinit var mDatabaseVersion:TextView
    private lateinit var mInspector:TextView
    private lateinit var mLoginFirstName:TextView
    private lateinit var mLoginLastName:TextView
    private lateinit var mGroup:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDiagnosticBinding.inflate(inflater,container,false)

        val userDetails = arguments?.getParcelable<Inspector>("userDetails")

        mLoginUser = binding.txtCurrentLoginName
        mLoginId = binding.txtCurrentLoginId
        mLoginFirstName = binding.txtCurrentLoginFirstName
        mLoginLastName = binding.txtCurrentLoginLastName
        mGroup = binding.txtCurrentLoginGroup

        mLoginUser.text = userDetails?.username
        mLoginId.text = userDetails?.employeeId.toString()
        mLoginFirstName.text =userDetails?.firstName
        mLoginLastName.text = userDetails?.lastName
        mGroup.text = userDetails?.groupName


        return binding.root
    }


    override fun onResume() {
        (requireContext() as MainActivity).setToolbarTitle(getString(R.string.action_diagnostic))
        super.onResume()
    }




}