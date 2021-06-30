package com.example.marketingcampaign.view.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
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
import com.example.marketingcampaign.databinding.MainFragmentBinding
import com.example.marketingcampaign.model.data.Target
import com.example.marketingcampaign.viewModel.main.MainViewModel

class MainFragment : MyFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    var tracker: SelectionTracker<Long>? = null
    private lateinit var viewModel: MainViewModel
    lateinit var listTarget : List<Target>
    val channelIds = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.view = this

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        val adapter = BasicDynamicAdapter(
            R.layout.simple_recycler_view_row,
            object : BasicDynamicAdapter.ViewHolderBindingListener<Target>() {

                override fun bind(view: View, item: Target, isActivated: Boolean) {
                    super.bind(view, item, isActivated)

                    val tv: TextView = view.findViewById(R.id.tv)

                    tv.text = item.name
                }
            })

        recyclerView.adapter = adapter

        showProgress()
        viewModel.getTargetApi()
        viewModel.getTargetList().observe(viewLifecycleOwner, { it ->
            it.let {
                adapter.items = it.toMutableList()
                listTarget = it
                hideProgress()
                Log.d("list", it.toString())
            }
        })

        tracker = SelectionTracker.Builder(
            "id",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            BasicDynamicAdapter.DetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()).withSelectionPredicate(
            SelectionPredicates.createSelectAnything())
            .build()

        if(savedInstanceState != null)
            tracker!!.onRestoreInstanceState(savedInstanceState)

        adapter.tracker = tracker

        tracker!!.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                if (tracker!!.selection.size() > 0) {

                    tracker!!.selection.map {

                        for (channel in listTarget[it.toInt()].channelId!!){
                            if(!channelIds.contains(channel))
                                channelIds.add(channel)
                        }

                    }
                }
            }
        })



        return root
    }

    fun onNextPress(){
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToChannelsFragment(channelIds.toIntArray()))
    }

}