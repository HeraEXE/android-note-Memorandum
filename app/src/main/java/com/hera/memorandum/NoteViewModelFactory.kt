package com.hera.memorandum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hera.memorandum.data.NoteRepository
import java.lang.IllegalArgumentException

class NoteViewModelFactory(private val db: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java))
            return NoteViewModel(db) as T
        else
            throw IllegalArgumentException("Unknown ViewModel Class.")
    }

}