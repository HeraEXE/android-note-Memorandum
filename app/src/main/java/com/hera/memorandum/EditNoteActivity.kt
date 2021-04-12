package com.hera.memorandum

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hera.memorandum.adapters.NoteListAdapter
import com.hera.memorandum.data.Note
import com.hera.memorandum.data.NoteDatabase
import com.hera.memorandum.data.NoteRepository
import com.hera.memorandum.databinding.ActivityNoteBinding


class EditNoteActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityNoteBinding
    private lateinit var viewModelFactory: NoteViewModelFactory
    private lateinit var viewModel: NoteViewModel

    // Getting extras from intent.
    private var id: Long = 0
    private var header: String = ""
    private var description: String = ""
    private var category: Int = 0

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
        supportActionBar?.setTitle(R.string.toolbar_edit_note_title)

        // Setting up button.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Getting extras from intent.
        id = intent.extras?.getLong(ID) ?: 0
        header = intent.extras?.getString(HEADER) ?: ""
        description = intent.extras?.getString(DESCRIPTION) ?: ""
        category = intent.extras?.getInt(CATEGORY) ?: 0

        // Setting view with extras data.
        viewBinding.headerBox.setText(header)
        viewBinding.descriptionBox.setText(description)
        val radioId = when(category) {
            NoteListAdapter.PHOENIX -> viewBinding.categoryButton1.id
            NoteListAdapter.DRAGON -> viewBinding.categoryButton2.id
            NoteListAdapter.UNICORN -> viewBinding.categoryButton3.id
            else -> -1
        }
        viewBinding.categoryChooser.check(radioId)

        // Setting submit button icon.
        viewBinding.submitButton.setImageResource(R.drawable.outline_edit_24)

        // Hiding keyboard.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        // Setting Submit Button click listener.
        viewBinding.submitButton.setOnClickListener {
            onSubmitButtonClick()
        }
    }

    // Setting Menu (note_menu).
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    // Setting menu items' behavior.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_icon) {
            val note = Note(id, header, description, category)
            viewModel.deleteNote(note)
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            Toast.makeText(this, getText(R.string.note_was_deleted), Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
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
        category = when (radioId) {
            viewBinding.categoryButton1.id -> NoteListAdapter.PHOENIX
            viewBinding.categoryButton2.id -> NoteListAdapter.DRAGON
            viewBinding.categoryButton3.id -> NoteListAdapter.UNICORN
            else -> NoteListAdapter.PHOENIX
        }

        // Define header.
        header = viewBinding.headerBox.text.toString()
        // Check if header is not empty.
        if (header == "") {
            Toast.makeText(this, getText(R.string.header_is_empty), Toast.LENGTH_SHORT).show()
            return
        }

        // Define description.
        description = viewBinding.descriptionBox.text.toString()

        // Create note instance and edit old one in the database.
        val note = Note(id, header, description, category)
        viewModel.editNote(note)

        // Get back to MainActivity and pop off NoteActivity from stack.
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        Toast.makeText(this, getText(R.string.note_was_edited), Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    companion object {
        const val ID = "id"
        const val HEADER = "header"
        const val DESCRIPTION = "description"
        const val CATEGORY = "category"
    }
}
