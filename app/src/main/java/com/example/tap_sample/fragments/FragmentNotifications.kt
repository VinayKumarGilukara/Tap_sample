package com.example.tap_sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tap_sample.MainActivity
import com.example.tap_sample.R
import com.example.tap_sample.adapters.NotificationsAdapter
import com.example.tap_sample.database.Notifications
import com.example.tap_sample.databinding.FragmentNotificationsBinding
import com.example.tap_sample.services.NotificationLiveData
import com.example.tap_sample.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentNotifications : Fragment() {
    private val loginViewModel:LoginViewModel by viewModels()
    private lateinit var binding: FragmentNotificationsBinding
    private val notifications = mutableListOf<Notifications>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val baseInflater = LayoutInflater.from(requireActivity())
        binding = FragmentNotificationsBinding.inflate(baseInflater,container,false)
        val empty = binding.emptyNotification


        //notifications.add(Notifications(1, "You have a new message!","Oops! Something Went Wrong"))

         recyclerView = binding.list
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = NotificationsAdapter(requireContext(), notifications)

        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.allNotifications.observe(viewLifecycleOwner, { notificationsList ->
            notifications.clear()
            notifications.addAll(notificationsList)
            recyclerView.adapter?.notifyDataSetChanged()
        })

    }

    override fun onResume() {
        val activity = activity as? MainActivity
        activity?.setToolbarTitle(getString(R.string.action_notifications))
        super.onResume()
    }

}