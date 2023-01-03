package com.crazylegend.audiopicker.dialogs.single

import android.Manifest
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.crazylegend.audiopicker.adapters.single.AudioSingleAdapter
import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.contracts.SinglePickerContracts
import com.crazylegend.audiopicker.listeners.onAudioPicked
import com.crazylegend.audiopicker.listeners.recycleBitmapsDSL
import com.crazylegend.audiopicker.modifiers.SingleAudioPickerModifier
import com.crazylegend.audiopicker.pickers.SingleAudioPicker
import com.crazylegend.core.abstracts.AbstractBottomSheetDialogFragment
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.extensions.viewBinding


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleAudioPickerBottomSheetDialog : AbstractBottomSheetDialogFragment(), SinglePickerContracts {

    override val layout: Int
        get() = super.layout
    override var onAudioPicked: onAudioPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutBinding::bind)
    override val audiosVM by viewModels<AudiosVM>()
    override val modifier: SingleAudioPickerModifier? get() = arguments?.getParcelable(modifierTag)

    private val singleAudioAdapter by lazy {
        AudioSingleAdapter(modifier?.viewHolderPlaceholderModifier, modifier?.viewHolderTitleTextModifier) {
            recycleThubmnail(it.thumbnail)
            setFragmentResult(SingleAudioPicker.SINGLE_AUDIO_REQUEST_KEY, bundleOf(SingleAudioPicker.ON_SINGLE_AUDIO_PICK_KEY to it))
            onAudioPicked?.forAudio(it)
            dismissAllowingStateLoss()
        }
    }

    override val askForStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            audiosVM.loadAudios()
        } else {
            Log.e(errorTag, "PERMISSION DENIED")
            dismissAllowingStateLoss()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            askForStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            askForStoragePermission.launch(Manifest.permission.READ_MEDIA_AUDIO)
        }
        setupUIForSinglePicker(binding.gallery, singleAudioAdapter, binding.title, binding.loadingIndicator, modifier?.loadingIndicatorTint, ::applyTitleModifications)
        audiosVM.audio.observe(viewLifecycleOwner) {
            setupList(singleAudioAdapter, it, binding.noContentText, modifier?.noContentTextModifier)
        }

        handleUIIndicator(audiosVM.loadingIndicator, binding.loadingIndicator)
        audiosVM.onShouldRecycleBitmaps = recycleBitmapsDSL {
            recycleBitmaps()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onAudioPicked = null
    }


    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun addModifier(modifier: SingleAudioPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

    override fun recycleBitmaps() {
        singleAudioAdapter.currentList.asSequence().forEach {
            it?.thumbnail?.apply {
                if (!isRecycled)
                    recycle()
            }
        }
    }

    private fun recycleThubmnail(thumbnail: Bitmap?) {
        thumbnail?.apply {
            if (!isRecycled) {
                recycle()
            }
        }
    }


}