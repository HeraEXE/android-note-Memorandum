package com.hera.memorandum

import androidx.lifecycle.ViewModel
import com.hera.memorandum.data.Note
import com.hera.memorandum.data.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteViewModel(private val db: NoteRepository) : ViewModel() {

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Phoenix Notes.
    private var _phoenixNotes = db.getPhoenixNotes()
    val phoenixNotes get() = _phoenixNotes

    // Dragon Notes.
    private var _dragonNotes = db.getDragonNotes()
    val dragonNotes get() = _dragonNotes

    // Unicorn Notes.
    private var _unicornNotes = db.getUnicornNotes()
    val unicornNotes get() = _unicornNotes

    // Define addNote function.
    fun addNote(note: Note) {
        uiScope.launch {
            db.addNote(note)
        }
    }

    // Define editNote function.
    fun editNote(note: Note) {
        uiScope.launch {
            db.editNote(note)
        }
    }

    // Define deleteNote function.
    fun deleteNote(note: Note) {
        uiScope.launch {
            db.deleteNote(note)
        }
    }

    // On Cleared.
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}