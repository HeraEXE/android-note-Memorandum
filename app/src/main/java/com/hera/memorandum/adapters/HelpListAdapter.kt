package com.hera.memorandum.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hera.memorandum.R
import com.hera.memorandum.data.HelpItem

/**
 * It is used for {viewBinding.recycler in HelpActivity}.
 */
class HelpListAdapter : RecyclerView.Adapter<HelpListAdapter.ViewHolder>() {

    // List of Help Items.
    private val items = listOf(
            // Phoenix.
            HelpItem(R.string.help_item_header1,
                    R.string.help_item_description1
            ),
            // Dragon.
            HelpItem(R.string.help_item_header2,
                    R.string.help_item_description2
            ),
            // Unicorn.
            HelpItem(R.string.help_item_header3,
                    R.string.help_item_description3
            )
    )

    //View Holder
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView = view.findViewById(R.id.help_header)
        val description: TextView = view.findViewById(R.id.help_description)
    }

    // On Create View Holder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.help_view_item, parent, false)
        return ViewHolder(view)
    }

    // Get Item Count.
    override fun getItemCount(): Int {
        return items.size
    }

    // On Bind View Holder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.header.setText(items[position].header)
        holder.description.setText(items[position].description)
    }
}