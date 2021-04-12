package com.hera.memorandum.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Dao for {NoteDatabase}.
 */
@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun editNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table WHERE category=0")
    fun getPhoenixNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE category=1")
    fun getDragonNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE category=2")
    fun getUnicornNotes(): LiveData<List<Note>>
}