package jp.axel.gambatte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun kanjiList(view: View) {
        startActivity(Intent(this, KanjiListActivity::class.java))
    }

    fun vocabulary(view: View) {
        startActivity(Intent(this, VocabularyListActivity::class.java))
    }
}