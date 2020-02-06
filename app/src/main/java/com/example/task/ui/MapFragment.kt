package com.example.task.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.task.MapActivity
import com.example.task.R


class MapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gotoMap = view.findViewById<View>(com.example.task.R.id.gotomap) as Button
        // set on-click listener
        gotoMap.setOnClickListener {
            val intent = Intent (requireActivity(), MapActivity::class.java)
            requireActivity()!!.startActivity(intent)
        }

    }
}
