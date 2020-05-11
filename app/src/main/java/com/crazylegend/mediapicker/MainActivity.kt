package com.crazylegend.mediapicker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.modifiers.TextModifier
import com.crazylegend.imagepicker.pickers.MultiImagePicker
import com.crazylegend.imagepicker.pickers.SingleImagePicker
import com.crazylegend.videopicker.pickers.MultiVideoPicker
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideoModel
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var clickedID = R.id.singleImageBottomSheetPick

    private val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    when (clickedID) {
                        //images
                        R.id.singleImageBottomSheetPick -> {
                            showSingleImageBottomSheetPicker()
                        }
                        R.id.singleImageDialogPick -> {
                            showSingleImageDialogPicker()
                        }
                        R.id.imageBottomSheetMultiPick -> {
                            showImageMultiBottomSheetPicker()
                        }
                        R.id.imagePickDialogMultiPick -> {
                            showImageMultiDialogPicker()
                        }

                        //videos
                        R.id.singleVideoBottomSheetPick -> {
                            showSingleVideoBottomSheetPicker()
                        }
                        R.id.singleVideoDialogPick -> {
                            showSingleVideoDialogPicker()
                        }
                        R.id.videoBottomSheetMultiPick -> {
                            showVideoMultiBottomSheetPicker()
                        }
                        R.id.videoPickDialogMultiPick -> {
                            showVideoMultiDialogPicker()
                        }
                    }
                } else {
                    Log.e("PERMISSION", "NOT ALLOWED!")
                }
            }

    //videos
    private fun showVideoMultiDialogPicker() {
        MultiVideoPicker.dialogPicker(this, ::doSomethingWithVideoList)
    }

    private fun showVideoMultiBottomSheetPicker() {
        MultiVideoPicker.bottomSheetPicker(this, ::doSomethingWithVideoList)
    }

    private fun showSingleVideoDialogPicker() {
        SingleVideoPicker.dialogPicker(this, ::loadVideo)
    }

    private fun showSingleVideoBottomSheetPicker() {
        SingleVideoPicker.bottomSheetPicker(this, ::loadVideo)
    }

    private fun loadVideo(videoModel: VideoModel) {
        Glide.with(this)
                .load(videoModel.contentUri)
                .into(video)
        Log.d("VIDEO_PICKED", videoModel.toString())
    }

    private fun doSomethingWithVideoList(list: List<VideoModel>) {
        list.forEach {
            Log.d("VIDEO LIST size ${list.size}", it.toString())
        }
    }

    //images
    private fun showImageMultiDialogPicker() {
        MultiImagePicker.dialogPicker(this, ::doSomethingWithImageList)
    }


    private fun showImageMultiBottomSheetPicker() {
        MultiImagePicker.bottomSheetPicker(this, ::doSomethingWithImageList)
    }

    private fun showSingleImageDialogPicker() {
        SingleImagePicker.dialogPicker(this, {
            invoke({
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TextModifier.TextStyle.BOLD
                textColor = Color.RED
                margin = 12 // use dp or sp this is only for demonstration purposes
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 16f  // use sp this is only for demonstration purposes
                textString = "Pick some images"
            }, {
                endMargin = 20  // use dp or sp this is only for demonstration purposes
                marginBottom = 20 // use dp or sp this is only for demonstration purposes
                padding = 10 // use dp or sp this is only for demonstration purposes
                resID = R.drawable.ic_close
            })
        }, ::loadImage)
    }

    private fun showSingleImageBottomSheetPicker() {
        SingleImagePicker.bottomSheetPicker(this,  {
            invoke({
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textStyle = TextModifier.TextStyle.BOLD_ITALIC
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.MAGENTA
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 20f  // use sp this is only for demonstration purposes
                textString = "Bottom sheet pick"
            })
        }, ::loadImage)
    }

    private fun loadImage(imageModel: ImageModel) {
        Glide.with(this)
                .load(imageModel.contentUri)
                .into(image)
        Log.d("IMAGE_PICKED", imageModel.toString())
    }

    private fun doSomethingWithImageList(list: List<ImageModel>) {
        list.forEach {
            Log.d("IMAGE LIST size ${list.size}", it.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //images
        singleImageBottomSheetPick.setOnClickListener(this)
        singleImageDialogPick.setOnClickListener(this)
        imageBottomSheetMultiPick.setOnClickListener(this)
        imagePickDialogMultiPick.setOnClickListener(this)

        //videos
        singleVideoBottomSheetPick.setOnClickListener(this)
        singleVideoDialogPick.setOnClickListener(this)
        videoBottomSheetMultiPick.setOnClickListener(this)
        videoPickDialogMultiPick.setOnClickListener(this)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //images
        MultiImagePicker.restoreListener(this, ::doSomethingWithImageList)
        SingleImagePicker.restoreListener(this, ::loadImage)

        //videos
        MultiVideoPicker.restoreListener(this, ::doSomethingWithVideoList)
        SingleVideoPicker.restoreListener(this, ::loadVideo)
    }

    override fun onClick(clickedview: View?) {
        clickedview ?: return
        clickedID = clickedview.id
        askForStoragePermission(READ_EXTERNAL_STORAGE)
    }

}
