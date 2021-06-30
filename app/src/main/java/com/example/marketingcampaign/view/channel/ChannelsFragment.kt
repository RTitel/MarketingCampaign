package com.example.marketingcampaign.view.channel

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketingcampaign.MyFragment
import com.example.marketingcampaign.viewModel.channel.ChannelsViewModel
import com.example.marketingcampaign.R
import com.example.marketingcampaign.adapter.BasicDynamicAdapter
import com.example.marketingcampaign.databinding.ChannelsFragmentBinding
import com.example.marketingcampaign.model.data.Channels
import com.example.marketingcampaign.view.monthlyFee.MonthlyFeeFragmentDirections

class ChannelsFragment : MyFragment() {

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private lateinit var viewModel: ChannelsViewModel
    var tracker: SelectionTracker<Long>? = null
    lateinit var channelSelect : Channels
    val listForRecycler = mutableListOf<Channels>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ChannelsViewModel::class.java)

        val channels = ChannelsFragmentArgs.fromBundle(requireArguments()).channels.toMutableList()

        val binding: ChannelsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.channels_fragment, container, false)
        val root = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.view = this

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        val adapter = BasicDynamicAdapter(
            R.layout.simple_recycler_view_row,
            object : BasicDynamicAdapter.ViewHolderBindingListener<Channels>() {

                override fun bind(view: View, item: Channels, isActivated: Boolean) {
                    super.bind(view, item, isActivated)

                    val tv: TextView = view.findViewById(R.id.tv)

                    tv.text = item.name
                }
            })

        recyclerView.adapter = adapter

        showProgress()
        viewModel.getChannelApi()
        viewModel.getChannelsList().observe(viewLifecycleOwner, { it ->
            it.let {

                for (channel in channels){
                        listForRecycler.add(it[channel])
                }

                adapter.items = listForRecycler.distinct() as MutableList<Channels>
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
                        channelSelect = listForRecycler[it.toInt()]
                    }
                }
            }
        })


        return root
    }

    fun onNextPress() {
        if(this::channelSelect.isInitialized) {
            findNavController().navigate(
                ChannelsFragmentDirections.actionChannelsFragmentToMonthlyFeeFragment(
                    channelSelect.id
                )
            )
        }else{
            Toast.makeText(requireContext(), "Please select one channel", Toast.LENGTH_SHORT).show()
        }

    }


}