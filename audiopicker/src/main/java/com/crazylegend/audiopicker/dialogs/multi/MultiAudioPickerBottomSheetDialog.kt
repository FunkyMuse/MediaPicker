package com.crazylegend.audiopicker.dialogs.multi

import android.Manifest
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
import com.crazylegend.audiopicker.adapters.multi.AudioMultiSelectAdapter
import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.contracts.MultiPickerContracts
import com.crazylegend.audiopicker.listeners.onAudiosPicked
import com.crazylegend.audiopicker.listeners.recycleBitmapsDSL
import com.crazylegend.audiopicker.modifiers.MultiAudioPickerModifier
import com.crazylegend.audiopicker.pickers.MultiAudioPicker
import com.crazylegend.core.abstracts.AbstractBottomSheetDialogFragment
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.extensions.viewBinding
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiAudioPickerBottomSheetDialog : AbstractBottomSheetDialogFragment(),
        MultiPickerContracts {


    override val layout: Int
        get() = super.layout
    override var onAudiosPicked: onAudiosPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutMultiBinding::bind)
    override val audiosVM by viewModels<AudiosVM>()
    override val modifier: MultiAudioPickerModifier?
        get() = arguments?.getParcelable(modifierTag)

    private val audioMultiSelectAdapter by lazy {
        AudioMultiSelectAdapter(modifier, modifier?.viewHolderPlaceholderModifier, modifier?.viewHolderTitleTextModifier)
    }
    override val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
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
        setupUIForMultiPicker(
                binding.gallery, audioMultiSelectAdapter, binding.doneButton, binding.title, binding.loadingIndicator, modifier?.loadingIndicatorTint,
                ::applyDoneButtonModifications, ::applyTitleModifications)

        audiosVM.audio.observe(viewLifecycleOwner) {
            setupList(audioMultiSelectAdapter, it, binding.noContentText, modifier?.noContentTextModifier)
        }

        binding.doneButton.setOnClickListener {
            val selectedList = audioMultiSelectAdapter.currentList.filter { it.isSelected }
            setFragmentResult(MultiAudioPicker.MULTI_AUDIO_REQUEST_KEY, bundleOf(MultiAudioPicker.ON_MULTI_AUDIO_PICK_KEY to selectedList))
            onAudiosPicked?.forAudios(selectedList)
            dismissAllowingStateLoss()
        }

        handleUIIndicator(audiosVM.loadingIndicator, binding.loadingIndicator)
        audiosVM.onShouldRecycleBitmaps = recycleBitmapsDSL {
            recycleBitmaps()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        onAudiosPicked = null
    }

    override fun addModifier(modifier: MultiAudioPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun applyDoneButtonModifications(doneButton: MaterialButton) {
        modifier?.doneButtonModifier?.applyImageParams(doneButton)
    }

    override fun recycleBitmaps() {
        audioMultiSelectAdapter.currentList.asSequence().forEach {
            it?.thumbnail?.apply {
                if (!isRecycled)
                    recycle()
            }
        }
    }
}