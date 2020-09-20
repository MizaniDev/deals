package com.mizanidev.deals.util

import android.content.Context
import android.view.Gravity
import com.valdesekamdem.library.mdtoast.MDToast

class CToast(val context: Context) {

    fun showSuccess(message: String) {
        val cToast = MDToast.makeText(context, message,
            MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS)
        cToast.setGravity(Gravity.TOP or Gravity.RIGHT,
            50, 50)
        cToast.show()
    }

    fun showWarning(message: String) {
        val cToast = MDToast.makeText(context, message,
            MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING)
        cToast.setGravity(Gravity.TOP or Gravity.RIGHT,
            50, 50)
        cToast.show()
    }

    fun showError(message: String) {
        val cToast = MDToast.makeText(context, message,
            MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR)
        cToast.setGravity(Gravity.TOP or Gravity.RIGHT,
            50, 50)
        cToast.show()
    }

    fun showInfo(message: String) {
        val cToast = MDToast.makeText(context, message,
            MDToast.LENGTH_SHORT, MDToast.TYPE_INFO)
        cToast.setGravity(Gravity.TOP or Gravity.RIGHT,
            50, 50)
        cToast.show()
    }
}