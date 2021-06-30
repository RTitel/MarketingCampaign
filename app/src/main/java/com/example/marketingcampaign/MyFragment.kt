package com.example.marketingcampaign

import androidx.fragment.app.Fragment
import com.example.marketingcampaign.view.progressDialog.ProgressDialog

open class MyFragment : Fragment() {

    fun showProgress() {
        ProgressDialog.show(parentFragmentManager, null)
    }

    fun hideProgress() {
        if (!ProgressDialog.isHidden) {
            ProgressDialog.dismiss()
        }
    }

}