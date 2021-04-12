package com.hera.memorandum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hera.memorandum.adapters.NoteListAdapter
import com.hera.memorandum.data.Note
import com.hera.memorandum.data.NoteDatabase
import com.hera.memorandum.data.NoteRepository
import com.hera.memorandum.databinding.ActivityNoteBinding

/**
 * In this activity user can create notes.
 */
class NoteActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityNoteBinding
    private lateinit var viewModelFactory: NoteViewModelFactory
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting view binding and content view.
        viewBinding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Setting ViewModel.
        val db = NoteRepository(NoteDatabase.getInstance(this).noteDao())
        viewModelFactory = NoteViewModelFactory(db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        // Setting action bar and its title.
        setSupportActionBar(viewBinding.actionBar.toolbar)
        supportActionBar?.setTitle(R.string.toolbar_note_title)

        // Setting up button.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Hiding keyboard.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        // Setting Submit Button click listener.
        viewBinding.submitButton.setOnClickListener {
            onSubmitButtonClick()
        }
    }

    // On Submit Button Click.
    private fun onSubmitButtonClick() {
        val radioId = viewBinding.categoryChooser.checkedRadioButtonId

        // Check if category was chosen.
        if (radioId == -1) {
            Toast.makeText(this, getText(R.string.category_is_not_chosen), Toast.LENGTH_SHORT).show()
            return
        }
        // Define category.
        val category = when (radioId) {
            viewBinding.categoryButton1.id -> NoteListAdapter.PHOENIX
            viewBinding.categoryButton2.id -> NoteListAdapter.DRAGON
            viewBinding.categoryButton3.id -> NoteListAdapter.UNICORN
            else -> NoteListAdapter.PHOENIX
        }

        // Define header.
        val header: String = viewBinding.headerBox.text.toString()
        // Check if header is not empty.
        if (header == "") {
            Toast.makeText(this, getText(R.string.header_is_empty), Toast.LENGTH_SHORT).show()
            return
        }

        // Define description.
        val description = viewBinding.descriptionBox.text.toString()

        // Create note instance and add it to database.
        val note = Note(0, header, description, category)
        viewModel.addNote(note)

        // Get back to MainActivity and pop off NoteActivity from stack.
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        Toast.makeText(this, getText(R.string.note_was_added), Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}