package com.hera.memorandum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hera.memorandum.adapters.HelpListAdapter
import com.hera.memorandum.databinding.ActivityHelpBinding

/**
 * This activity holds helpful info for new users.
 */
class HelpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting view binding and content view.
        viewBinding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Setting action bar and its title.
        setSupportActionBar(viewBinding.actionBar.toolbar)
        supportActionBar?.setTitle(R.string.toolbar_help_title)

        // Setting up button.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setting recycler adapter.
        viewBinding.recycler.adapter = HelpListAdapter()
    }
}