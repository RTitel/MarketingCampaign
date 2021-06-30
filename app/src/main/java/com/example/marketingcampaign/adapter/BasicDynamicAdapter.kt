package com.example.marketingcampaign.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.ParcelUuid
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.marketingcampaign.interfaces.IEntityAdapterTrackerKeyProvider

class BasicDynamicAdapter<T>(private val resId: Int, private val listener: ViewHolderBindingListener<T>):
RecyclerView.Adapter<BasicDynamicAdapter.ViewHolder<T>>() {

    var tracker: SelectionTracker<Long>? = null

    private var _items: MutableList<T> = mutableListOf()
    var items: MutableList<T>
        get() = _items
        set(value) {
            _items = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
                override fun inSelectionHotspot(e: MotionEvent) = true
            }
    }

    abstract class ViewHolderBindingListener<T> {

        open fun bind(view: View, item: T, isActivated: Boolean) {
            view.isActivated = isActivated
        }
    }

    class DetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {

        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as ViewHolder<*>).getItemDetails()
            }
            return null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(resId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val item = items[position]

        if (tracker != null) {
            tracker!!.let {
                listener.bind(holder.itemView, item, it.isSelected(position.toLong()))
            }
        }
        else {
            listener.bind(holder.itemView, item, false)
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = items.size

    fun getItem(key: Int): T? {
        return items[key]
    }

    fun getItemPosition(e: T): Int {
        return items.indexOf(e)
    }
}