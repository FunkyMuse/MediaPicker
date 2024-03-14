package com.crazylegend.mediapicker

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.pickers.MultiAudioPicker
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.dto.PickerConfig
import com.crazylegend.core.modifiers.SizeTextModifier
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.pickers.MultiImagePicker
import com.crazylegend.imagepicker.pickers.SingleImagePicker
import com.crazylegend.mediapicker.databinding.ActivityMainBinding
import com.crazylegend.videopicker.pickers.MultiVideoPicker
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideoModel

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var clickedID = R.id.singleImageBottomSheetPick

    private val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    when (clickedID) {
                        //images
                        R.id.singleImageBottomSheetPick -> {
                            showSingleImageBottomSheetPicker()
                        }

                        R.id.imageBottomSheetMultiPick -> {
                            showImageMultiBottomSheetPicker()
                        }


                        //videos
                        R.id.singleVideoBottomSheetPick -> {
                            showSingleVideoBottomSheetPicker()
                        }

                        R.id.videoBottomSheetMultiPick -> {
                            showVideoMultiBottomSheetPicker()
                        }


                        //audios
                        R.id.singleAudioBottomSheetPick -> {
                            showSingleAudioBottomSheetPicker()
                        }

                        R.id.audioBottomSheetMultiPick -> {
                            showAudioMultiBottomSheetPicker()
                        }


                        //fragment result
                        R.id.launchFragmentResult -> {
                            val dialog = FragmentResult()
                            dialog.show(supportFragmentManager, FragmentResult::class.java.name)
                        }
                    }
                } else {
                    Log.e("PERMISSION", "NOT ALLOWED!")
                }
            }

    //audios
    private fun showSingleAudioBottomSheetPicker() {
        SingleAudioPicker.showPicker(this, {
            setupViewHolderTitleText {
                textColor = Color.BLACK
                textPadding = 10 // use dp or sp this is only for demonstration purposes
            }
            setupBaseModifier(loadingIndicatorColor = R.color.minusColor, titleTextModifications = {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.ITALIC
                textColor = Color.BLACK
                marginBottom = 30 // use dp or sp this is only for demonstration purposes
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 30f  // use sp this is only for demonstration purposes
                textString = "Pick an audio"
            }, placeHolderModifications = {
                resID = R.drawable.ic_image
            })
        }, ::loadAudio)
    }


    private fun showAudioMultiBottomSheetPicker() {
        MultiAudioPicker.showPicker(this, {
            setupViewHolderTitleText {
                textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
                textStyle = TitleTextModifier.TextStyle.BOLD
                textPadding = 10 // use dp or sp this is only for demonstration purposes
            }
            setupBaseMultiPicker(tintForLoadingProgressBar = ContextCompat.getColor(
                    this@MainActivity, R.color.colorPrimaryDark
            ),
                    gravityForSelectAndUnSelectIndicators = BaseMultiPickerModifier.Gravity.BOTTOM_LEFT,
                    titleModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multiple songs"
                    },
                    selectIconModifications = {
                        resID = R.drawable.ic_checked
                        tint = Color.BLACK
                    },
                    unSelectIconModifications = {
                        resID = R.drawable.ic_unchecked
                        tint = Color.BLACK
                    },
                    viewHolderPlaceholderModifications = {
                        resID = R.drawable.ic_album_second
                    })
        }, ::doSomethingWithAudioList)
    }

    private fun loadAudio(audioModel: AudioModel) {
        Glide.with(this).load(audioModel.loadThumbnail(contentResolver)).error(R.drawable.ic_album)
                .into(binding.audio)
        binding.audioTitle.text = audioModel.displayName
        Log.d("AUDIO_PICKED ${audioModel.thumbnail?.isRecycled}", audioModel.toString())
    }

    private fun doSomethingWithAudioList(list: List<AudioModel>) {
        list.forEach {
            Log.d("AUDIO LIST size ${list.size}", it.toString())
        }
    }

    //videos

    private fun showVideoMultiBottomSheetPicker() {
        MultiVideoPicker.showPicker(this, extensions = arrayOf(), pickerConfig = PickerConfig(showFileSize = true), {
            setupBaseMultiPicker(tintForLoadingProgressBar = ContextCompat.getColor(
                    this@MainActivity, R.color.colorPrimaryDark
            ),
                    gravityForSelectAndUnSelectIndicators = BaseMultiPickerModifier.Gravity.TOP_RIGHT,
                    titleModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multiple videos"
                    },
                    selectIconModifications = {
                        resID = R.drawable.ic_checked
                        tint = Color.BLACK
                    },
                    unSelectIconModifications = {
                        resID = R.drawable.ic_unchecked
                        tint = Color.BLACK
                    },
                    viewHolderPlaceholderModifications = {
                        resID = R.drawable.ic_close
                    },
                    sizeTextModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        textStyle = SizeTextModifier.TextStyle.BOLD
                        margin = 22 // use dp or sp this is only for demonstration purposes
                        textPadding = 8 // use dp or sp this is only for demonstration purposes
                        textSize = 10f  // use sp this is only for demonstration purposes
                    }
                    )
        }, ::doSomethingWithVideoList)
    }


    private fun showSingleVideoBottomSheetPicker() {
        //SingleVideoPicker.showPicker(context = this, onPickedVideo = ::loadVideo)

        SingleVideoPicker.showPicker(this, extensions = arrayOf(), pickerConfig = PickerConfig(showFileSize = true), {
            setupBaseModifier(loadingIndicatorColor = R.color.minusColor, titleTextModifications = {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.ITALIC
                textColor = Color.BLACK
                marginBottom = 30 // use dp or sp this is only for demonstration purposes
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 30f  // use sp this is only for demonstration purposes
                textString = "Pick a video"
            }, placeHolderModifications = {
                resID = R.drawable.ic_image
            }, sizeTextModifications = {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = SizeTextModifier.TextStyle.NORMAL
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.BLACK
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 12f  // use sp this is only for demonstration purposes
                backgroundDrawable = R.drawable.rounded_bg_abstract_dialog
            }
            )
        }, ::loadVideo)

    }

    private fun loadVideo(videoModel: VideoModel) {
        Glide.with(this).load(videoModel.contentUri).into(binding.video)
        Log.d("VIDEO_PICKED", videoModel.toString())
    }

    private fun doSomethingWithVideoList(list: List<VideoModel>) {
        list.forEach {
            Log.d("VIDEO LIST size ${list.size}", it.toString())
        }
    }

    //images

    private fun showImageMultiBottomSheetPicker() {
        MultiImagePicker.showPicker(this, extensions = arrayOf(), pickerConfig = PickerConfig(showFileSize = true), {
            setupBaseMultiPicker(tintForLoadingProgressBar = ContextCompat.getColor(
                    this@MainActivity, R.color.colorPrimaryDark
            ),
                    gravityForSelectAndUnSelectIndicators = BaseMultiPickerModifier.Gravity.TOP_LEFT,
                    titleModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multiple images"
                    },
                    selectIconModifications = {
                        resID = R.drawable.ic_checked
                        tint = Color.BLACK
                    },
                    unSelectIconModifications = {
                        resID = R.drawable.ic_unchecked
                        tint = Color.BLACK
                    },
                    viewHolderPlaceholderModifications = {
                        resID = R.drawable.ic_image
                    },
                    sizeTextModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = SizeTextModifier.TextStyle.NORMAL
                        margin = 22 // use dp or sp this is only for demonstration purposes
                        textColor = Color.GREEN
                        textPadding = 6 // use dp or sp this is only for demonstration purposes
                        textSize = 12f  // use sp this is only for demonstration purposes
                    }
            )
        }) { list ->
            doSomethingWithImageList(list)
        }
    }


    private fun showSingleImageBottomSheetPicker() {
        SingleImagePicker.showPicker(this, extensions = arrayOf(), pickerConfig = PickerConfig(showFileSize = true), {
            loadingIndicatorTint =
                    ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
            titleTextModifier.apply {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.BLACK
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 20f  // use sp this is only for demonstration purposes
                textString = "Select an image"
            }
            noContentTextModifier.apply {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.RED
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 20f  // use sp this is only for demonstration purposes
            }
            viewHolderPlaceholderModifier.apply {
                resID = R.drawable.ic_image
            }
            sizeTextModifier.apply {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = SizeTextModifier.TextStyle.NORMAL
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.BLACK
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 12f  // use sp this is only for demonstration purposes
                backgroundDrawable = R.drawable.rounded_bg_abstract_dialog
            }
        }, ::loadImage)
    }

    private fun loadImage(imageModel: ImageModel) {
        Glide.with(this).load(imageModel.contentUri).into(binding.image)
        Log.d("IMAGE_PICKED", imageModel.toString())
    }

    private fun doSomethingWithImageList(list: List<ImageModel>) {
        list.forEach {
            Log.d("IMAGE LIST size ${list.size}", it.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //images
        binding.singleImageBottomSheetPick.setOnClickListener(this)
        binding.imageBottomSheetMultiPick.setOnClickListener(this)

        //videos
        binding.singleVideoBottomSheetPick.setOnClickListener(this)
        binding.videoBottomSheetMultiPick.setOnClickListener(this)

        //audios
        binding.singleAudioBottomSheetPick.setOnClickListener(this)
        binding.audioBottomSheetMultiPick.setOnClickListener(this)

        //fragment result
        binding.launchFragmentResult.setOnClickListener(this)
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            askForStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            askForStoragePermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
    }

}
