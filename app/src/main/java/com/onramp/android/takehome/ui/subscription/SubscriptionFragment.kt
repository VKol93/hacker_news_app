package com.onramp.android.takehome.ui.subscription

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onramp.android.takehome.R
import com.onramp.android.takehome.service.SubscribeService
import kotlinx.android.synthetic.main.fragment_subscribe.*

class SubscriptionFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_subscribe, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeButton.setOnClickListener {
            val intent = Intent(requireContext(), SubscribeService::class.java)
            val keyword:String = keywordEditText.text.toString()
            intent.putExtra("keyword", keyword)
            requireContext().startService(intent)
        }
    }
}