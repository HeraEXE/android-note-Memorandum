package com.hera.memorandum

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
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
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private var isDarkMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting Shared Prefs.
        sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        prefsEditor = sharedPrefs.edit()

        // Getting values from the Shared Prefs.
        isDarkMode = sharedPrefs.getBoolean(MODE, false)

        // Setting Mode.
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

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
        //val tab = viewBinding.tabLayout.getTabAt(1)
        //tab?.select()
    }


    // Setting Menu (main_menu).
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Setting menu items' behavior.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.help_icon -> startActivity(Intent(this, HelpActivity::class.java))
            R.id.mode_icon -> {
                if (isDarkMode) {
                    prefsEditor.putBoolean(MODE, false)
                    prefsEditor.apply()
                    item.setIcon(R.drawable.outline_dark_mode_24)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    prefsEditor.putBoolean(MODE, true)
                    prefsEditor.apply()
                    item.setIcon(R.drawable.outline_light_mode_24)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Categories Names.
    companion object {
        const val MODE = "MODE"
        const val PHOENIX = R.string.phoenix_text
        const val DRAGON = R.string.dragon_text
        const val UNICORN = R.string.unicorn_text
    }
}