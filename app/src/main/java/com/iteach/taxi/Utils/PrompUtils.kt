package com.iteach.taxi.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StyleRes


object PromptUtils {
        fun alertDialog(context: Context, @StyleRes style: Int, dialogBuilder: AlertDialog.Builder.() -> Unit): Dialog {
            val builder = AlertDialog.Builder(context, style).also {
                it.setCancelable(false)
                it.dialogBuilder()
            }
            return builder.create()
        }

        fun AlertDialog.Builder.negativeButton(text: String = "Yo'q", handleClick: (dialogInterface: DialogInterface) -> Unit = {}) {
            this.setPositiveButton(text) { dialogInterface, which -> handleClick(dialogInterface) }
        }

        fun AlertDialog.Builder.positiveButton(text: String = "Ha", handleClick: (dialogInterface: DialogInterface) -> Unit = {}) {
            this.setNegativeButton(text) { dialogInterface, which -> handleClick(dialogInterface) }
        }
    }