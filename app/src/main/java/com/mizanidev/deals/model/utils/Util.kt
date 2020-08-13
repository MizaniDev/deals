package com.mizanidev.deals.model.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.TextView
import com.mizanidev.deals.R
import java.text.CharacterIterator
import java.text.StringCharacterIterator


class Util{
    companion object{
        fun boldDescriptionText(textView: TextView){
            val spannable = SpannableString(textView.text)
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, textView.text.indexOf(":") + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = spannable
        }

        fun gameSizeReadable(bytes: Long) : String{
            var bytesReceived = bytes
            if (-1000 < bytesReceived && bytesReceived < 1000) {
                return bytesReceived.toString().plus(" B")
            }

            val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
            while (bytesReceived <= -999950 || bytesReceived >= 999950) {
                bytesReceived /= 1000
                ci.next()
            }

            return String.format("%.1f %cB", bytesReceived / 1000.0, ci.current())
        }

        fun showSimpleAlert(context: Context, message: String){
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(message)
            alertDialog.setNeutralButton(R.string.fechar, null)
            alertDialog.show()
        }
    }

}