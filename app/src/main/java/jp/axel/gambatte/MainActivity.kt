package jp.axel.gambatte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainBtnGroup: MaterialButtonToggleGroup = findViewById(R.id.main_btn_group)

        mainBtnGroup.addOnButtonCheckedListener { _, _, _ ->
            mainBtnGroup.clearChecked()
        }
    }

    fun kanjiList(view: View) {
        startActivity(Intent(this, KanjiListActivity::class.java))
    }

    fun vocabulary(view: View) {
        startActivity(Intent(this, VocabularyListActivity::class.java))
    }
}