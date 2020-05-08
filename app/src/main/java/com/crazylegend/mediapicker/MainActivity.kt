package com.crazylegend.mediapicker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.crazylegend.imagepicker.picker.SingleImagePicker
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var clickedID = R.id.bsPick

    private val askForStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                when (clickedID) {
                    R.id.bsPick -> {
                        showBottomSheetPicker()
                    }
                    R.id.dialogPick -> {
                        showDialogImagePicker()
                    }
                }
            } else {
                Log.d("PERMISSION!", "NOT ALLOWED!")
            }
        }

    private fun showDialogImagePicker() {
        SingleImagePicker.dialogPicker(this) {
            loadImage(it.contentUri)
        }
    }

    private fun showBottomSheetPicker() {
        SingleImagePicker.bottomSheetPicker(this) {
            loadImage(it.contentUri)
        }
    }

    private fun loadImage(contentUri: Uri) {
        Glide.with(this)
            .load(contentUri)
            .into(image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bsPick.setOnClickListener(this)
        dialogPick.setOnClickListener(this)

    }

    override fun onClick(clickedview: View?) {
        clickedview ?: return
        clickedID = clickedview.id
        askForStoragePermission(READ_EXTERNAL_STORAGE)
    }

}
