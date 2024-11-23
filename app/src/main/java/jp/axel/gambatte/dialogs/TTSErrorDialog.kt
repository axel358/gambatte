package jp.axel.gambatte.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import jp.axel.gambatte.R

class TTSErrorDialog(context: Context): MaterialAlertDialogBuilder(context) {
    init {
        setTitle(R.string.tts_error_title)
        setMessage(R.string.tts_error_message)
        setNegativeButton(R.string.install_tts) { _, _ ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com")))
        }
        setPositiveButton(R.string.manage_languages) { _, _ -> 
            context.startActivity(Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA))
        }
        setNeutralButton(android.R.string.cancel) { _, _ -> }
        show()
    }
}