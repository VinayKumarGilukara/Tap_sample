package com.example.tap_sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.tap_sample.databinding.FragmentCountDialogBinding
import java.text.SimpleDateFormat
import java.util.Calendar


class FragmentCountDialog : DialogFragment() {
    private lateinit var binding: FragmentCountDialogBinding
    private lateinit var title:TextView
    private lateinit var mCalender:Calendar
    private lateinit var date :String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCountDialogBinding.inflate(inflater,container,false)
        title = binding.title
        mCalender = Calendar.getInstance()
        val df = SimpleDateFormat("MMM d, yyyy")
        date = df.format(mCalender.time)
        title.text = "Counts for $date"

        return binding.root

    }

}