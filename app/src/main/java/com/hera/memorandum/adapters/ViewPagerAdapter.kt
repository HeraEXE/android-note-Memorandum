package com.hera.memorandum.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hera.memorandum.MainActivity
import com.hera.memorandum.NoteViewModel
import com.hera.memorandum.R

/**
 * It is used for {viewBinding.pager in MainActivity}.
 */
class ViewPagerAdapter(private val activity: MainActivity, private val viewModel: NoteViewModel) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    // ViewHolder.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recycler: RecyclerView = view.findViewById(R.id.recycler)
    }

    // On Create View Holder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pager_view_item, parent, false)
        return ViewHolder(view)
    }

    // Get Item Count.
    override fun getItemCount(): Int {
        return CATEGORIES
    }

    // On Bind View Holder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recycler.adapter = NoteListAdapter(activity, viewModel, position)
        holder.recycler.layoutManager = GridLayoutManager(activity, 2)
    }

    // Holds categories quantity.
    companion object {
        const val CATEGORIES = 3
    }
}