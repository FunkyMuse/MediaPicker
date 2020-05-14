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
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.pickers.MultiAudioPicker
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.imagepicker.images.ImageModel
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

                        //audios
                        R.id.singleAudioBottomSheetPick -> {
                            showSingleAudioBottomSheetPicker()
                        }
                        R.id.singleAudioDialogPick -> {
                            showSingleAudioDialogPicker()
                        }
                        R.id.audioBottomSheetMultiPick -> {
                            showAudioMultiBottomSheetPicker()
                        }
                        R.id.audioPickDialogMultiPick -> {
                            showAudioMultiDialogPicker()
                        }
                    }
                } else {
                    Log.e("PERMISSION", "NOT ALLOWED!")
                }
            }

    //audios

    private fun showSingleAudioBottomSheetPicker() {
        SingleAudioPicker.bottomSheetPicker(this, {
            setup(singlePicker = {
                setupTitleOnly {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.BOLD
                    textColor = Color.RED
                    margin = 12 // use dp or sp this is only for demonstration purposes
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 16f  // use sp this is only for demonstration purposes
                    textString = "Pick some songs"
                }
            }, viewHolderTitle = {
                textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
            }, viewHolderPlaceHolder = {
                resID = R.drawable.ic_album
            })
        }, ::loadAudio)
    }

    private fun showSingleAudioDialogPicker() {
        SingleAudioPicker.dialogPicker(this, {
            setup(singlePicker = {
                setup(imageText = {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.BOLD
                    textColor = Color.RED
                    margin = 12 // use dp or sp this is only for demonstration purposes
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 16f  // use sp this is only for demonstration purposes
                    textString = "Pick some songs"
                }, closeButton = {
                    endMargin = 20  // use dp or sp this is only for demonstration purposes
                    marginBottom = 20 // use dp or sp this is only for demonstration purposes
                    padding = 10 // use dp or sp this is only for demonstration purposes
                    resID = R.drawable.ic_close
                })
            }, viewHolderTitle = {
                textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
            }, viewHolderPlaceHolder = {
                resID = R.drawable.ic_album
            })
        }, ::loadAudio)
    }

    private fun showAudioMultiDialogPicker() {
       MultiAudioPicker.dialogPicker(this, {
           setup(
                   multiPicker = {
                       setup(
                               gravityForIndicators = MultiPickerModifier.Gravity.TOP_RIGHT,
                               titleText = {
                                   textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                   textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                                   textColor = Color.BLUE
                                   marginBottom = 30 // use dp or sp this is only for demonstration purposes
                                   textPadding = 5 // use dp or sp this is only for demonstration purposes
                                   textSize = 30f  // use sp this is only for demonstration purposes
                                   textString = "Pick multiple songs"
                               },
                               selectIcon = {
                                   resID = R.drawable.ic_checked
                                   tint = Color.LTGRAY
                               },
                               unSelectIcon = {
                                   resID = R.drawable.ic_unchecked
                                   tint = Color.LTGRAY
                               }
                       )
                   },
                   viewHolderTitle = {
                       textColor = Color.DKGRAY
                       textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                       textPadding = 10 // use dp or sp this is only for demonstration purposes
                   },
                   viewHolderPlaceHolder = {
                       resID = R.drawable.ic_album_second
                   }
           )
       },::doSomethingWithAudioList)
    }

    private fun showAudioMultiBottomSheetPicker() {
        MultiAudioPicker.bottomSheetPicker(this, {
            setup(
                    multiPicker = {
                        setup(
                                gravityForIndicators = MultiPickerModifier.Gravity.BOTTOM_LEFT,
                                titleText = {
                                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                    textStyle = TitleTextModifier.TextStyle.ITALIC
                                    textColor = Color.MAGENTA
                                    marginBottom = 30 // use dp or sp this is only for demonstration purposes
                                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                                    textSize = 30f  // use sp this is only for demonstration purposes
                                    textString = "Pick multiple songs"
                                },
                                selectIcon = {
                                    resID = R.drawable.ic_checked
                                    tint = Color.MAGENTA
                                },
                                unSelectIcon = {
                                    resID = R.drawable.ic_unchecked
                                    tint = Color.MAGENTA
                                }
                        )
                    },
                    viewHolderTitle = {
                        textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
                        textStyle = TitleTextModifier.TextStyle.BOLD
                        textPadding = 10 // use dp or sp this is only for demonstration purposes
                    },
                    viewHolderPlaceHolder = {
                        resID = R.drawable.ic_album_second
                    }
            )
        },::doSomethingWithAudioList)
    }

    private fun loadAudio(audioModel: AudioModel) {
        Glide.with(this)
                .load(audioModel.loadThumbnail(contentResolver))
                .error(R.drawable.ic_album)
                .into(audio)
        audioTitle.text = audioModel.displayName
        Log.d("AUDIO_PICKED", audioModel.toString())
    }

    private fun doSomethingWithAudioList(list: List<AudioModel>) {
        list.forEach {
            Log.d("AUDIO LIST size ${list.size}", it.toString())
        }
    }

    //videos
    private fun showVideoMultiDialogPicker() {
        MultiVideoPicker.dialogPicker(this, {
            setup(
                    gravityForIndicators = MultiPickerModifier.Gravity.TOP_LEFT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.RED
                        margin = 15 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some multi videos"
                    },
                    selectIcon = {
                        tint = Color.YELLOW
                        marginBottom = 5
                        endMargin = 5
                    },
                    unSelectIcon = {
                        tint = Color.YELLOW
                        marginBottom = 5
                        endMargin = 5
                    }
            )
        }, ::doSomethingWithVideoList)
    }

    private fun showVideoMultiBottomSheetPicker() {
        MultiVideoPicker.bottomSheetPicker(this, {
            setup(
                    gravityForIndicators = MultiPickerModifier.Gravity.BOTTOM_LEFT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.MAGENTA
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multi videos"
                    },
                    selectIcon = {
                        tint = Color.MAGENTA
                    },
                    unSelectIcon = {
                        tint = Color.MAGENTA
                    }
            )
        }, ::doSomethingWithVideoList)
    }

    private fun showSingleVideoDialogPicker() {
        SingleVideoPicker.dialogPicker(this, {
            setup({
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.BOLD
                textColor = Color.RED
                margin = 12 // use dp or sp this is only for demonstration purposes
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 16f  // use sp this is only for demonstration purposes
                textString = "Pick some videos"
            }, {
                endMargin = 20  // use dp or sp this is only for demonstration purposes
                marginBottom = 20 // use dp or sp this is only for demonstration purposes
                padding = 10 // use dp or sp this is only for demonstration purposes
                resID = R.drawable.ic_close
            })
        }, ::loadVideo)
    }

    private fun showSingleVideoBottomSheetPicker() {
        SingleVideoPicker.bottomSheetPicker(this, {
            setup({
                textString = "Picking some of them videos"
            }, {
                resID = R.drawable.ic_close
            })
        }, ::loadVideo)
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
        MultiImagePicker.dialogPicker(this, {
            setup(
                    gravityForIndicators = MultiPickerModifier.Gravity.TOP_RIGHT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textColor = Color.RED
                        margin = 12 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some multi images"
                    },
                    doneButton = {
                        cornerRadius = 20  // use dp/sp/px this is only for demonstration purposes
                    },
                    selectIcon = {
                        margin = 15 // use dp or sp this is only for demonstration purposes
                        tint = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                    },
                    unSelectIcon = {
                        margin = 15 // use dp or sp this is only for demonstration purposes
                        tint = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                    })
        }, ::doSomethingWithImageList)
    }


    private fun showImageMultiBottomSheetPicker() {
        MultiImagePicker.bottomSheetPicker(this, {
            setup(
                    gravityForIndicators = MultiPickerModifier.Gravity.BOTTOM_LEFT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textColor = Color.RED
                        margin = 12 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some multi images"
                    },
                    doneButton = {
                        cornerRadius = 20  // use dp/sp/px this is only for demonstration purposes
                        tint = ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
                    },
                    selectIcon = {
                        resID = R.drawable.ic_checked
                    },
                    unSelectIcon = {
                        resID = R.drawable.ic_unchecked
                    })
        }) { list ->
            doSomethingWithImageList(list)
        }
    }

    private fun showSingleImageDialogPicker() {
        SingleImagePicker.dialogPicker(this, {
            setup({
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.BOLD
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
        SingleImagePicker.bottomSheetPicker(this, {
            setup({
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.MAGENTA
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 20f  // use sp this is only for demonstration purposes
                textString = "Bottom sheet pick images"
            }, {
                resID = R.drawable.ic_close
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

        //audios
        singleAudioBottomSheetPick.setOnClickListener(this)
        singleAudioDialogPick.setOnClickListener(this)
        audioBottomSheetMultiPick.setOnClickListener(this)
        audioPickDialogMultiPick.setOnClickListener(this)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //images
        MultiImagePicker.restoreListener(this, ::doSomethingWithImageList)
        SingleImagePicker.restoreListener(this, ::loadImage)

        //videos
        MultiVideoPicker.restoreListener(this, ::doSomethingWithVideoList)
        SingleVideoPicker.restoreListener(this, ::loadVideo)

        //audios
        MultiAudioPicker.restoreListener(this, ::doSomethingWithAudioList)
        SingleAudioPicker.restoreListener(this, ::loadAudio)
    }


    override fun onClick(clickedview: View?) {
        clickedview ?: return
        clickedID = clickedview.id
        askForStoragePermission(READ_EXTERNAL_STORAGE)
    }

}
