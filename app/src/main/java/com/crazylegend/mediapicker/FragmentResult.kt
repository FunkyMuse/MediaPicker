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
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.crazylegend.audiopicker.audios.AudioModel
import com.crazylegend.audiopicker.pickers.MultiAudioPicker
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.pickers.MultiImagePicker
import com.crazylegend.imagepicker.pickers.SingleImagePicker
import com.crazylegend.mediapicker.databinding.ActivityMainBinding
import com.crazylegend.videopicker.pickers.MultiVideoPicker
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/24/20 to long live and prosper !
 */
class FragmentResult : DialogFragment(R.layout.activity_main), View.OnClickListener {

    private var binding: ActivityMainBinding? = null

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

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

                }
            } else {
                Log.e("PERMISSION", "NOT ALLOWED!")
            }
        }

    //audios
    @SuppressLint("MissingPermission")
    private fun showSingleAudioBottomSheetPicker() {
        SingleAudioPicker.showPicker(requireContext(), {
            setupViewHolderTitleText {
                textColor = Color.BLACK
                textPadding = 10 // use dp or sp this is only for demonstration purposes
            }
            setupBaseModifier(
                loadingIndicatorColor = R.color.minusColor,
                titleTextModifications = {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.ITALIC
                    textColor = Color.BLACK
                    marginBottom = 30 // use dp or sp this is only for demonstration purposes
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 30f  // use sp this is only for demonstration purposes
                    textString = "Pick an audio"
                },
                placeHolderModifications = {
                    resID = R.drawable.ic_image
                }
            )
        })
    }


    @SuppressLint("MissingPermission")
    private fun showAudioMultiBottomSheetPicker() {
        MultiAudioPicker.showPicker(requireContext(), {
            setupViewHolderTitleText {
                textColor = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
                textStyle = TitleTextModifier.TextStyle.BOLD
                textPadding = 10 // use dp or sp this is only for demonstration purposes
            }
            setupBaseMultiPicker(
                tintForLoadingProgressBar = ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
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
                }
            )
        })
    }

    private fun loadAudio(audioModel: AudioModel) {
        Glide.with(this)
            .load(audioModel.loadThumbnail(requireContext().contentResolver))
            .error(R.drawable.ic_album)
            .into(binding!!.audio)
        binding!!.audioTitle.text = audioModel.displayName
        Log.d("AUDIO_PICKED ${audioModel.thumbnail?.isRecycled}", audioModel.toString())
    }

    private fun doSomethingWithAudioList(list: List<AudioModel>) {
        list.forEach {
            Log.d("AUDIO LIST size ${list.size}", it.toString())
        }
    }

    //videos

    @SuppressLint("MissingPermission")
    private fun showVideoMultiBottomSheetPicker() {
        MultiVideoPicker.showPicker(requireContext(), arrayOf(), {
            setupBaseMultiPicker(
                tintForLoadingProgressBar = ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
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
                }
            )
        })
    }

    @SuppressLint("MissingPermission")

    private fun showSingleVideoBottomSheetPicker() {
        //SingleVideoPicker.showPicker(context = this, onPickedVideo = ::loadVideo)

        SingleVideoPicker.showPicker(requireContext(), extensions = arrayOf(), {
            setupBaseModifier(
                loadingIndicatorColor = R.color.minusColor,
                titleTextModifications = {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.ITALIC
                    textColor = Color.BLACK
                    marginBottom = 30 // use dp or sp this is only for demonstration purposes
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 30f  // use sp this is only for demonstration purposes
                    textString = "Pick a video"
                },
                placeHolderModifications = {
                    resID = R.drawable.ic_image
                }
            )
        })

    }

    private fun loadVideo(videoModel: VideoModel) {
        Glide.with(this)
            .load(videoModel.contentUri)
            .into(binding!!.video)
        Log.d("VIDEO_PICKED", videoModel.toString())
    }

    private fun doSomethingWithVideoList(list: List<VideoModel>) {
        list.forEach {
            Log.d("VIDEO LIST size ${list.size}", it.toString())
        }
    }

    //images
    @SuppressLint("MissingPermission")
    private fun showImageMultiBottomSheetPicker() {
        MultiImagePicker.showPicker(requireContext(), extensions = arrayOf(), {
            setupBaseMultiPicker(
                tintForLoadingProgressBar = ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPrimaryDark
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
                }
            )
        })
    }

    @SuppressLint("MissingPermission")
    private fun showSingleImageBottomSheetPicker() {
        SingleImagePicker.showPicker(requireContext(), extensions = arrayOf(), {
            loadingIndicatorTint =
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
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
        })
    }

    private fun loadImage(imageModel: ImageModel) {
        Glide.with(this)
            .load(imageModel.contentUri)
            .into(binding!!.image)
        Log.d("IMAGE_PICKED", imageModel.toString())
    }

    private fun doSomethingWithImageList(list: List<ImageModel>) {
        list.forEach {
            Log.d("IMAGE LIST size ${list.size}", it.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivityMainBinding.bind(view)

        binding!!.launchFragmentResult.hide()

        //images
        binding!!.singleImageBottomSheetPick.setOnClickListener(this)
        binding!!.imageBottomSheetMultiPick.setOnClickListener(this)

        //videos
        binding!!.singleVideoBottomSheetPick.setOnClickListener(this)
        binding!!.videoBottomSheetMultiPick.setOnClickListener(this)

        //audios
        binding!!.singleAudioBottomSheetPick.setOnClickListener(this)
        binding!!.audioBottomSheetMultiPick.setOnClickListener(this)


        //listeners images
        setFragmentResultListener(SingleImagePicker.SINGLE_IMAGE_REQUEST_KEY) { _, bundle ->
            val loadedModel =
                bundle.getParcelable<ImageModel>(SingleImagePicker.ON_SINGLE_IMAGE_PICK_KEY)
            loadedModel?.let { loadImage(it) }
        }
        setFragmentResultListener(MultiImagePicker.MULTI_IMAGE_REQUEST_KEY) { _, bundle ->
            val loadedModel =
                bundle.getParcelableArrayList<ImageModel>(MultiImagePicker.ON_MULTI_IMAGE_PICK_KEY)
            loadedModel?.let { doSomethingWithImageList(it) }
        }

        //listeners video
        setFragmentResultListener(SingleVideoPicker.SINGLE_VIDEO_REQUEST_KEY) { _, bundle ->
            val loadedModel =
                bundle.getParcelable<VideoModel>(SingleVideoPicker.ON_SINGLE_VIDEO_PICK_KEY)
            loadedModel?.let { loadVideo(it) }
        }
        setFragmentResultListener(MultiVideoPicker.MULTI_VIDEO_REQUEST_KEY) { _, bundle ->
            val loadedModel =
                bundle.getParcelableArrayList<VideoModel>(MultiVideoPicker.ON_MULTI_VIDEO_PICK_KEY)
            loadedModel?.let { doSomethingWithVideoList(it) }
        }

        //listeners audio
        setFragmentResultListener(SingleAudioPicker.SINGLE_AUDIO_REQUEST_KEY) { _, bundle ->
            val loadedModel =
                bundle.getParcelable<AudioModel>(SingleAudioPicker.ON_SINGLE_AUDIO_PICK_KEY)
            loadedModel?.let { loadAudio(it) }
        }
        setFragmentResultListener(MultiAudioPicker.MULTI_AUDIO_REQUEST_KEY) { _, bundle ->
            val loadedModel =
                bundle.getParcelableArrayList<AudioModel>(MultiAudioPicker.ON_MULTI_AUDIO_PICK_KEY)
            loadedModel?.let { doSomethingWithAudioList(it) }
        }

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