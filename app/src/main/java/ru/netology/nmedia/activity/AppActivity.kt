package ru.netology.nmedia.activity

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment.Companion.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app ) {

//    lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityAppBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        loadIntent()
    }

    private fun loadIntent() {
        intent?.let {
            if (it.action == ACTION_SEND) {
                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if (text.isNullOrBlank()) {
//                    Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
//                        .setAction(android.R.string.ok) {
//                        finish()
//                    }
//                        .show()
                    return@let
                } else {
                    intent.removeExtra(Intent.EXTRA_TEXT)
                    findNavController(R.id.newPostFragment).navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply{
                            textArg = text
                        }
                    )
//                    binding.newText.text = text
                }
            }
            }
        }
    }
