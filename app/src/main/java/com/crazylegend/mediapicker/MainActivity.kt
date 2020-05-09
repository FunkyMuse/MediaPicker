package com.crazylegend.mediapicker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.picker.MultiImagePicker
import com.crazylegend.imagepicker.picker.SingleImagePicker
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var clickedID = R.id.singleImageBottomSheetPick

    private val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    when (clickedID) {
                        R.id.singleImageBottomSheetPick -> {
                            showBottomSheetPicker()
                        }
                        R.id.singleImageDialogPick -> {
                            showDialogImagePicker()
                        }
                        R.id.imageBottomSheetMultiPick -> {
                            showMultiBottomSheetPicker()
                        }
                        R.id.imagePickDialogMultiPick -> {
                            showMultiDialogPicker()
                        }
                    }
                } else {
                    Log.e("PERMISSION", "NOT ALLOWED!")
                }
            }

    private fun showMultiDialogPicker() {
        MultiImagePicker.dialogPicker(this, ::doSomethingWithList)
    }


    private fun showMultiBottomSheetPicker() {
        MultiImagePicker.bottomSheetPicker(this, ::doSomethingWithList)
    }

    private fun showDialogImagePicker() {
        SingleImagePicker.dialogPicker(this, ::loadImage)
    }

    private fun showBottomSheetPicker() {
        SingleImagePicker.bottomSheetPicker(this, ::loadImage)
    }

    private fun loadImage(imageModel: ImageModel) {
        Glide.with(this)
                .load(imageModel.contentUri)
                .into(image)
    }


    private fun doSomethingWithList(list: List<ImageModel>) {
        list.forEach {
            Log.d("LIST size ${list.size}", it.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singleImageBottomSheetPick.setOnClickListener(this)
        singleImageDialogPick.setOnClickListener(this)
        imageBottomSheetMultiPick.setOnClickListener(this)
        imagePickDialogMultiPick.setOnClickListener(this)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        MultiImagePicker.restoreListener(this, ::doSomethingWithList)
    }

    override fun onClick(clickedview: View?) {
        clickedview ?: return
        clickedID = clickedview.id
        askForStoragePermission(READ_EXTERNAL_STORAGE)
    }

}
