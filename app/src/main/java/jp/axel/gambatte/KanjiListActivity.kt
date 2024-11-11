package jp.axel.gambatte

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import jp.axel.gambatte.adapters.KanaItemAdapter

class KanjiListActivity : AppCompatActivity(), KanaItemAdapter.KanaClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kanji_list)
        val kanjiRv = findViewById<RecyclerView>(R.id.kanji_rv)
        val kanjis = assets.list("n5/kanji")!!.toList()
        kanjiRv.adapter = KanaItemAdapter(this,R.layout.kana_grid_item, kanjis, this)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onKanaClicked(kana: String, position: Int, view: View) {
        startActivity(
            Intent(this, ItemDetailsActivity::class.java)
                .putExtra("index", position)
                .putExtra("level", "n5")
                .putExtra("type", "kanji")
        )
    }

    fun practice(view: View) {
        startActivity(
            Intent(this, ItemDetailsActivity::class.java)
                .putExtra("level", "n5")
                .putExtra("type", "kanji")
                .putExtra("practice", true)
        )
    }
}