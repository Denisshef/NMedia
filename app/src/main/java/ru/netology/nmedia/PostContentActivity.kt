package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.ActivityPostContentBinding

class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contentPost = intent?.getStringExtra(RESULT_EDIT_KEY)
        if (contentPost != null) {
            binding.edit.text = contentPost.toEditable()
        }
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            val text = binding.edit.text
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = text.toString()
                intent.putExtra(RESULT_NEW_KEY, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

    object ResultContract : ActivityResultContract<String?, String?>() {
        override fun createIntent(context: Context, input: String?) =
            Intent(context, PostContentActivity::class.java).apply {
                putExtra(RESULT_EDIT_KEY, input)
            }

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_NEW_KEY)
            } else null
    }

    private companion object {
        private const val RESULT_NEW_KEY = "postNewContent"
        private const val RESULT_EDIT_KEY = "postEditContent"
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}