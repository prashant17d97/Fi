package com.prashant.fi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.prashant.fi.databinding.ActivityMainBinding

class MainActivity :AppCompatActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        observe()
    }

    /**
     * observe function is defined observe the value from live data act according
     *
     * 1 -> To toast the messages and error
     *
     * 2 -> To request the focus on the field, which one has an error
     *
     * 3 -> To finish the Activity
     * */
    private fun observe() {

        //1
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        //2
        viewModel.focusOn.observe(this) {
            this.focusToNext(it)
        }
        //3
        viewModel.finish.observe(this) {
            this.finish()
        }
    }

    private fun focusToNext(focusedOn: FocusedOn) {
        when (focusedOn) {
            FocusedOn.PAN -> {
                binding.etPan.requestFocus()
            }
            FocusedOn.DATE -> {
                binding.etDate.requestFocus()
            }
            FocusedOn.MONTH -> {
                binding.etMonth.requestFocus()
            }
            FocusedOn.YEAR -> {
                binding.etYear.requestFocus()
            }
        }

    }
}