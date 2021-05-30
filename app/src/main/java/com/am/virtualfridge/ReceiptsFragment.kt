package com.am.virtualfridge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.am.virtualfridge.R
import com.am.virtualfridge.fridge.Product
import com.google.firebase.database.DatabaseReference


class ReceiptsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_receipts, container, false)
    }
}