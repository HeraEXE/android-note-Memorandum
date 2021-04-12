package com.hera.memorandum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.hera.memorandum.adapters.ViewPagerAdapter
import com.hera.memorandum.data.NoteDatabase
import com.hera.memorandum.data.NoteRepository
import com.hera.memorandum.databinding.ActivityMainBinding


/**
 * This is the main activity with list of notes.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModelFactory: NoteViewModelFactory
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Defining View Binding and Content View.
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Defining View Model.
        val db = NoteRepository(NoteDatabase.getInstance(this).noteDao())
        viewModelFactory = NoteViewModelFactory(db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        // Setting Toolbar (toolbar).
        setSupportActionBar(viewBinding.actionBar.toolbar)

        // Setting new title to action bar.
        supportActionBar?.setTitle(R.string.toolbar_main_title)

        // Setting Pager adapter.
        viewBinding.pager.adapter = ViewPagerAdapter(this, viewModel)

        // Setting Phoenix Observer for pager.
        viewModel.phoenixNotes.observe(this, Observer {
            viewBinding.pager.adapter?.notifyDataSetChanged()
        })

        // Setting Dragon Observer for pager.
        viewModel.dragonNotes.observe(this, Observer {
            viewBinding.pager.adapter?.notifyDataSetChanged()
        })

        // Setting Unicorn Observer for pager.
        viewModel.unicornNotes.observe(this, Observer {
            viewBinding.pager.adapter?.notifyDataSetChanged()
        })

        // Setting New Note button click listener.
        viewBinding.newNote.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }

        // Setting Tab Layout.
        val tabList = listOf(PHOENIX, DRAGON, UNICORN)
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.pager) { tab, position ->
            tab.setText(tabList[position])
        }.attach()
    }


    // Setting Menu (main_menu).
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Setting menu items' behavior.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.help_icon)
            startActivity(Intent(this, HelpActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    // Categories Names.
    companion object {
        const val PHOENIX = R.string.phoenix_text
        const val DRAGON = R.string.dragon_text
        const val UNICORN = R.string.unicorn_text
    }
}