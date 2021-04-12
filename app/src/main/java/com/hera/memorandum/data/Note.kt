package com.hera.memorandum.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Note table is used for {NoteDatabase}.
 */
@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val header: String,

    val description: String,

    val category: Int
)