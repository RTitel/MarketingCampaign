package com.example.marketingcampaign.view.monthlyFee

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketingcampaign.MyFragment
import com.example.marketingcampaign.R
import com.example.marketingcampaign.adapter.BasicDynamicAdapter
import com.example.marketingcampaign.databinding.MonthlyFeeFragmentBinding
import com.example.marketingcampaign.model.data.Channels
import com.example.marketingcampaign.model.data.MonthlyFee
import com.example.marketingcampaign.viewModel.monthlyFee.MonthlyFeeViewModel


class MonthlyFeeFragment : MyFragment() {

    private lateinit var viewModel: MonthlyFeeViewModel
    var tracker: SelectionTracker<Long>? = null
    lateinit var monthlyFeeSelect : MonthlyFee
    val listForRecycler = mutableListOf<MonthlyFee>()
    var channel : Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MonthlyFeeViewModel::class.java)

        channel = MonthlyFeeFragmentArgs.fromBundle(requireArguments()).channel

        val binding: MonthlyFeeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.monthly_fee_fragment, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.view = this

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        val adapter = BasicDynamicAdapter(
            R.layout.monthly_fee_recycler_view_row,
            object : BasicDynamicAdapter.ViewHolderBindingListener<MonthlyFee>() {

                override fun bind(view: View, item: MonthlyFee, isActivated: Boolean) {
                    super.bind(view, item, isActivated)

                    val tvPrice: TextView = view.findViewById(R.id.tvPrice)
                    val tvFacilities: TextView = view.findViewById(R.id.tvFacilities)

                    tvPrice.text = item.price
                    tvFacilities.text = item.facilities
                }
            })

        recyclerView.adapter = adapter

        showProgress()
        viewModel.getMonthlyFeeApi()
        viewModel.getMonthlyFeesList().observe(viewLifecycleOwner, { it ->
            it.let {
                for (item in it){
                    if(item.chanelId == channel && !adapter.items.contains(item)){
                        listForRecycler.add(item)
                    }
                }
                adapter.items = listForRecycler.distinct() as MutableList<MonthlyFee>
                hideProgress()
            }
        })

        tracker = SelectionTracker.Builder(
            "id",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            BasicDynamicAdapter.DetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        )
            .build()

        if (savedInstanceState != null)
            tracker!!.onRestoreInstanceState(savedInstanceState)

        adapter.tracker = tracker

        tracker!!.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                if (tracker!!.selection.size() > 0) {

                    tracker!!.selection.map {
                        monthlyFeeSelect = listForRecycler[it.toInt()]
                    }
                }
            }
        })


        return root
    }


    fun saveSelectedMonthlyFeeDB(){

        viewModel.insert(monthlyFeeSelect)

    }

    fun onNextPress() {
        if(this::monthlyFeeSelect.isInitialized) {
            saveSelectedMonthlyFeeDB()
            findNavController().navigate(MonthlyFeeFragmentDirections.actionMonthlyFeeFragmentToReviewMonthlyFeeFragment())
        }else{
            Toast.makeText(requireContext(), "Please select one fee", Toast.LENGTH_SHORT).show()
        }
    }


    fun onBackChannel() {

        saveSelectedMonthlyFeeDB()
        goBackToChannels()
    }

    private fun goBackToChannels() {
        findNavController().popBackStack()
    }

}