package com.jhbb.coroutinesexceptions.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.jhbb.coroutinesexceptions.R
import java.lang.IllegalStateException

class LoadingDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setView(it.layoutInflater.inflate(R.layout.dialog_loading, null))
                .setCancelable(false)
                .create()
        } ?: throw IllegalStateException()
    }
}