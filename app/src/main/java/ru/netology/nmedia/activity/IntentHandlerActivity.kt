package ru.netology.nmedia.activity

import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntentHandlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadIntent()
    }

    private fun loadIntent() {
        intent?.let {
            if (it.action == ACTION_SEND) {
                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if (text.isNullOrBlank()) {
                    Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok) {
                        finish()
                    }
                        .show()
                    return@let
                }
                else binding.newText.text = text
            }
        }
    }
}