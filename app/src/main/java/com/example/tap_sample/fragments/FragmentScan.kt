package com.example.tap_sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.tap_sample.R
import com.example.tap_sample.database.Inspector
import com.example.tap_sample.database.ServiceType
import com.example.tap_sample.databinding.FragmentScanBinding
import com.example.tap_sample.preferences.TapPreferences
import com.example.tap_sample.viewmodels.LoginViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentScan : Fragment() {
    private lateinit var binding: FragmentScanBinding
    private val loginViewModel:LoginViewModel by activityViewModels()
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private val pref: TapPreferences? = null
    private lateinit var mOprName: String
    private var switchMaterial:SwitchMaterial? = null
    private var mTxtSwitchText:TextView? = null
    private lateinit var serviceType:TextView
    private var mRead:Button? = null
    private var selectedTransitType: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentScanBinding.inflate(inflater, container, false)

        binding.txtHeading.visibility = View.GONE
        binding.btnIssueCitation.visibility = View.GONE
        binding.btnWrite.visibility = View.GONE

        val userDetails = arguments?.getParcelable<Inspector>("userDetails")


        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar1)
        val mOprLayout = toolbar.findViewById<LinearLayout>(R.id.title_layout)
        val mOprTitle = toolbar.findViewById<TextView>(R.id.actionbar_opr)
        val mLineRoute = toolbar.findViewById<TextView>(R.id.actionbar_route)
         switchMaterial = toolbar.findViewById(R.id.material_switch) as SwitchMaterial
         mTxtSwitchText = toolbar.findViewById(R.id.textMode)
         serviceType = toolbar.findViewById(R.id.txt_service_type)
         mRead = binding.btnRead

        mOprTitle.text = String.format(getString(R.string.actionbar_opr),userDetails?.operator)

        mOprName = pref?.getOperator().toString()

      /*  mOprName = userDetails?.operator.toString()

        if (mOprName != null) {
            mOprTitle.text = String.format(
                getString(R.string.actionbar_opr),
                mOprName
            )

        } else {
            mOprName = ""
            mOprTitle.text = String.format(
                getString(R.string.actionbar_opr),
                mOprName
            )

        }*/



        mOprLayout.setOnClickListener {

            launchRouteDialog(mOprName)

        }

        val mLinearBottomLayout: LinearLayout = binding.bottomSheetLayout
        bottomSheetBehavior = BottomSheetBehavior.from(mLinearBottomLayout)

        binding.historyMorph.setOnClickListener(View.OnClickListener {
            if ((bottomSheetBehavior as BottomSheetBehavior<*>).getState() != BottomSheetBehavior.STATE_EXPANDED) {
                (bottomSheetBehavior as BottomSheetBehavior<*>).setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                (bottomSheetBehavior as BottomSheetBehavior<*>).setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        })

        (bottomSheetBehavior as BottomSheetBehavior<*>).addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.historyMorph.setImageResource(R.drawable.ic_action_down)
                } else {
                    binding.historyMorph.setImageResource(R.drawable.ic_action_up)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        loginViewModel.selectedValue.observe(viewLifecycleOwner) { selectedValue ->
            mLineRoute.post {
                mLineRoute.text = selectedValue
                viewLifecycleOwner.lifecycleScope.launch {
                    val lineRoute = withContext(Dispatchers.IO) {
                        loginViewModel.getLineRouteByDescription(selectedValue)
                    }
                    selectedTransitType = lineRoute?.transitType ?: -1

                   if (selectedTransitType != -1) {
                       loadServiceTypes(selectedTransitType)
                   }
               }
                setupViewModel()

            }

        }

        binding.btnRead.setOnClickListener(){
            readyRead()
        }
        serviceType.setOnClickListener(){
           setupViewModel()
        }

        switchMaterial?.setOnClickListener(View.OnClickListener {
            switchMaterial?.isChecked = switchMaterial!!.isChecked
        })

        switchMaterial!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                mTxtSwitchText!!.text = getString(R.string.continue_text)
                mRead!!.visibility = View.GONE

            } else {
                mTxtSwitchText!!.text = getString(R.string.normal)
                mRead!!.visibility = View.VISIBLE

            }
        }


        return binding.root

    }

    private fun launchRouteDialog(oprName: String) {
        val fm = requireActivity().supportFragmentManager
        val dialog = FragmentRouteDialog()
        val bun = Bundle()
        bun.putString(FragmentRouteDialog.BUNDLE_OPERATOR, oprName)
        dialog.arguments = bun
        dialog.show(fm, getString(R.string.frag_route_dialog))
    }

    private fun readyRead() {
        val fm = requireActivity().supportFragmentManager
        val dialog = FragmentReadyToScan()
        dialog.show(fm, "scan_dialog")

    }

    private fun setupViewModel() {
        loginViewModel.serviceTypeList.observe(viewLifecycleOwner, Observer { serviceTypes ->
            lifecycleScope.launch {
                showServiceTypeDialog(serviceTypes)
            }
        })
    }

    private suspend fun loadServiceTypes(selectedTransitType: Int) {
        withContext(Dispatchers.Main) {
            loginViewModel.getServiceTypeList(selectedTransitType)
        }
    }


    private suspend fun showServiceTypeDialog(serviceTypes: List<ServiceType>) {
        withContext(Dispatchers.Main) {
            val uniqueServiceTypeSet = mutableSetOf<String>()

            for (serviceType in serviceTypes) {
                val formattedValue = "SL${serviceType.serviceTypeId}"
                uniqueServiceTypeSet.add(formattedValue)
            }

            val serviceTypeNames = uniqueServiceTypeSet.toList()

            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Select Service Type")
                .setItems(serviceTypeNames.toTypedArray()) { _, which ->
                    val selectedServiceType = serviceTypes.find { serviceType ->
                        "SL${serviceType.serviceTypeId}" == serviceTypeNames[which]
                    }

                    serviceType.text = "SL${selectedServiceType?.serviceTypeId.toString()}"
                }
                .setCancelable(false)
                .show()
        }
    }
}