package com.myfood.popup

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.myfood.R

class Popup {
    companion object {
        fun showInfo(
            context: Context,
            resources: Resources,
            msg: String,
            okBtnFun: (() -> Unit)? = null
        ) {
            Handler(Looper.getMainLooper()).post {
                val dialog = AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage(msg)
                    .setPositiveButton("OK") { view, _ ->
                        view.dismiss()
                        okBtnFun?.invoke()
                    }
                    .setCancelable(false)
                    .create()
                dialog.show()
                val b2: Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                b2.setTextColor(resources.getColor(R.color.turquoise))
            }
        }

        fun showConfirm(
            context: Context,
            resources: Resources,
            msg: String,
            yesBtn: String,
            noBtn: String,
            okBtnFun: (() -> Unit)? = null
        ) {
            Handler(Looper.getMainLooper()).post {
                val dialog = AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage(msg)
                    .setPositiveButton(yesBtn) { view, _ ->
                        view.dismiss()
                        okBtnFun?.invoke()
                    }
                    .setNegativeButton(noBtn) { view, _ ->
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()
                dialog.show()
                val b1: Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                val b2: Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                b1.setTextColor(resources.getColor(R.color.turquoise))
                b2.setTextColor(resources.getColor(R.color.turquoise))
            }
        }
    }
}