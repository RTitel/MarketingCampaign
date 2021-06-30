package com.example.marketingcampaign.view.progressDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.marketingcampaign.R
import com.example.marketingcampaign.databinding.FragmentProgressDialogBinding
import com.example.marketingcampaign.viewModel.progressDialog.ProgressDialogViewModel

object ProgressDialog: DialogFragment() {

    private lateinit var model: ProgressDialogViewModel
    private var visible: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        model = ViewModelProvider(this).get(
            ProgressDialogViewModel::class.java)
        val binding: FragmentProgressDialogBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress_dialog, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = model

        isCancelable = false

        return root
    }

    fun show(manager: FragmentManager) {

        if (!visible) {
            super.show(manager, null)
            visible = true
        }
    }

    fun hide() {

        if (visible) {
            super.dismiss()
            visible = false
        }
    }
}