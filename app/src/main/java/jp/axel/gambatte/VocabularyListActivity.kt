package jp.axel.gambatte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import jp.axel.gambatte.adapters.KanaItemAdapter

class VocabularyListActivity : AppCompatActivity(), KanaItemAdapter.KanaClickListener {
    private lateinit var wordsRv: RecyclerView
var type = "verbs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary_list)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        wordsRv = findViewById(R.id.vocabulary_rv)
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                type = tab!!.text.toString().lowercase()
                loadData()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        loadData()
    }

    private fun loadData() {
        val words = assets.list("n5/vocabulary/$type")!!.toList().sortedWith(compareBy { it })
        wordsRv.adapter = KanaItemAdapter(this, R.layout.word_list_item, words, this)

    }

    override fun onKanaClicked(kana: String, position: Int, view: View) {
        startActivity(
            Intent(this, ItemDetailsActivity::class.java)
                .putExtra("index", position)
                .putExtra("level", "n5")
                .putExtra("type", "vocabulary/$type")
        )
    }

    fun practice(view: View) {
        startActivity(
            Intent(this, ItemDetailsActivity::class.java)
                .putExtra("level", "n5")
                .putExtra("type", "vocabulary/$type")
                .putExtra("practice", true)
        )
    }
}