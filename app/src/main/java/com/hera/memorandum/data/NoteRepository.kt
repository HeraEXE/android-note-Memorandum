package com.hera.memorandum.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun editNote(note: Note) {
        noteDao.editNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    fun getPhoenixNotes(): LiveData<List<Note>> {
        return noteDao.getPhoenixNotes()
    }

    fun getDragonNotes(): LiveData<List<Note>> {
        return noteDao.getDragonNotes()
    }

    fun getUnicornNotes(): LiveData<List<Note>> {
        return noteDao.getUnicornNotes()
    }
}