package jp.axel.gambatte

import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.io.InputStreamReader
import java.util.Locale

const val SPEAK_ID_ENGLISH = "english"
const val SPEAK_ID_JAPANESE = "japanese"

class ItemDetailsActivity : AppCompatActivity() {
    private var practice: Boolean = false
    private lateinit var type: String
    private lateinit var level: String
    private var itemIndex: Int = 0
    private lateinit var item: String
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var nameTv: TextView
    private lateinit var indexTv: TextView
    private lateinit var pronunciationTv: TextView
    private lateinit var meaningTv: TextView
    private lateinit var items: List<String>
    private lateinit var nextBtn: MaterialButton
    private lateinit var stopBtn: MaterialButton
    private lateinit var previousBtn: MaterialButton
    private lateinit var actionsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        nameTv = findViewById(R.id.item_name_tv)
        pronunciationTv = findViewById(R.id.item_pronunciation_tv)
        meaningTv = findViewById(R.id.item_meaning_tv)
        indexTv = findViewById(R.id.item_index_tv)
        nextBtn = findViewById(R.id.btn_next)
        previousBtn = findViewById(R.id.btn_previous)
        stopBtn = findViewById(R.id.stop_btn)
        actionsLayout = findViewById(R.id.actions_layout)

        nextBtn.setOnClickListener { next() }
        previousBtn.setOnClickListener { previous() }

        itemIndex = intent.getIntExtra("index", 0)
        type = intent.getStringExtra("type")!!
        level = intent.getStringExtra("level")!!
        practice = intent.getBooleanExtra("practice", false)
        val allItems = assets.list("$level/$type")!!.toList()
        if (type.startsWith("vocabulary"))
            nameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f)

        if (practice) {
            actionsLayout.visibility = View.GONE
            stopBtn.visibility = View.VISIBLE
            stopBtn.keepScreenOn = true
            stopBtn.setOnClickListener {
                if (::textToSpeech.isInitialized && textToSpeech.isSpeaking)
                    textToSpeech.stop()
                stopPractice()
            }
            val list = mutableListOf<String>()
            Utils.generateRandomNumbers(20.coerceAtMost(allItems.size), 0, allItems.lastIndex).forEach { number ->
                list.add(allItems[number])
            }
            items = list
        } else {
            items = allItems
        }

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val languageStatus = textToSpeech.setLanguage(Locale.JAPAN)
                if (languageStatus == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                    textToSpeech.setOnUtteranceProgressListener(object :
                        UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {

                        }

                        override fun onDone(utteranceId: String?) {
                            if (utteranceId == SPEAK_ID_ENGLISH && practice) {
                                if (itemIndex < items.lastIndex) {
                                    Handler(mainLooper).postDelayed({
                                        next()
                                    }, 500)
                                } else {
                                    Handler(mainLooper).post {
                                        stopPractice()
                                    }
                                }
                            }
                        }

                        override fun onError(utteranceId: String?) {

                        }

                    })
                }
            }
            loadItem()
        }


    }

    private fun loadItem() {
        item = items[itemIndex]
        val name = item.split("_")[0]
        nameTv.text = name
        assets.open("$level/$type/$item").use { inputStream ->
            val contents = InputStreamReader(inputStream).readLines()
            pronunciationTv.text = contents[0]
            meaningTv.text = contents[1]

            if (::textToSpeech.isInitialized) {
                speakJapanese(name)
                if (practice)
                    speakEnglish(contents[1])
            }
        }
        indexTv.text = "${itemIndex + 1}/${items.size}"
    }


    fun speak(view: View) {
        speakJapanese(item.split("_")[0])
    }

    private fun speakJapanese(text: String) {
        textToSpeech.setLanguage(Locale.JAPAN)
        textToSpeech.speak(text, 1, null, SPEAK_ID_JAPANESE)
    }

    private fun speakEnglish(text: String) {
        textToSpeech.setLanguage(Locale.US)
        textToSpeech.speak(text, 1, null, SPEAK_ID_ENGLISH)

    }

    private fun previous() {
        if (itemIndex > 0) {
            itemIndex--
            loadItem()
        }
    }

    fun next() {
        if (itemIndex < items.lastIndex) {
            itemIndex++
            loadItem()
        }
    }

    private fun stopPractice() {
        stopBtn.keepScreenOn = false
        stopBtn.visibility = View.GONE
        actionsLayout.visibility = View.VISIBLE
        practice = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::textToSpeech.isInitialized) {
            if (textToSpeech.isSpeaking)
                textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
}