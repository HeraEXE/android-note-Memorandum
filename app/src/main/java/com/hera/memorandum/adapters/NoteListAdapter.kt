package com.hera.memorandum.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hera.memorandum.*

/**
 * It is used for {recycler in ViewPagerAdapter}.
 */
class NoteListAdapter(private val activity: MainActivity,
                      private val viewModel: NoteViewModel,
                      private val category: Int)
    : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    // Setting list of notes by category.
    private val notes = when (category) {
        PHOENIX -> viewModel.phoenixNotes.value
        DRAGON -> viewModel.dragonNotes.value
        UNICORN -> viewModel.unicornNotes.value

        else -> viewModel.phoenixNotes.value
    }

    // ViewHolder.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteBox: TextView = view.findViewById(R.id.note_box)
    }

    // On Create View Holder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_view_item, parent, false)
        return ViewHolder(view)
    }

    // Get Item Count.
    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

    // On Bind View Holder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes!![position]
        holder.noteBox.text = note.header
        holder.noteBox.setOnClickListener {
            val intent: Intent = Intent(activity, EditNoteActivity::class.java)
            intent.putExtra(EditNoteActivity.ID, note.id)
            intent.putExtra(EditNoteActivity.HEADER, note.header)
            intent.putExtra(EditNoteActivity.DESCRIPTION, note.description)
            intent.putExtra(EditNoteActivity.CATEGORY, note.category)
            activity.startActivity(intent)
        }
    }

    // Holds Categories.
    companion object {
        const val PHOENIX = 0
        const val DRAGON = 1
        const val UNICORN = 2
    }
}