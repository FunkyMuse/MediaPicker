package com.crazylegend.audiopicker.dialogs.multi

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.crazylegend.audiopicker.adapters.multi.AudioMultiSelectAdapter
import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.consts.LIST_STATE
import com.crazylegend.audiopicker.contracts.MultiPickerContracts
import com.crazylegend.audiopicker.listeners.onAudiosPicked

import com.crazylegend.audiopicker.modifiers.MultiAudioPickerModifier
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
    override val multiSelectAdapter by lazy {
        AudioMultiSelectAdapter(modifier?.multiPickerModifier, modifier?.viewHolderPlaceholderModifier, modifier?.viewHolderTitleModifier)
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
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        setupUIForMultiPicker(savedInstanceState, LIST_STATE, multiSelectAdapter.selectedPositions,
                binding.gallery, multiSelectAdapter, binding.doneButton, binding.title, binding.loadingIndicator, modifier?.multiPickerModifier?.loadingIndicatorTint,
                ::applyDoneButtonModifications, ::applyTitleModifications)

        audiosVM.audio.observe(viewLifecycleOwner) {
            multiSelectAdapter.submitList(it)
        }

        binding.doneButton.setOnClickListener {
            onValuesPicked(multiSelectAdapter.selectedPositions, audiosVM.audio.value
                    ?: emptyList()) { list ->
                onAudiosPicked?.forAudios(list)
            }
        }

        handleUIIndicator(audiosVM.loadingIndicator, binding.loadingIndicator)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveSelectedPositionsState(multiSelectAdapter.selectedPositions, outState, LIST_STATE)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        onAudiosPicked = null
    }

    override fun onDestroy() {
        super.onDestroy()
        recycleBitmaps()
    }

    override fun addModifier(modifier: MultiAudioPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.multiPickerModifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun applyDoneButtonModifications(doneButton: MaterialButton) {
        modifier?.multiPickerModifier?.doneButtonModifier?.applyImageParams(doneButton)
    }

    override fun recycleBitmaps() {
        multiSelectAdapter.currentList.asSequence().forEach {
            it?.thumbnail?.apply {
                if (!isRecycled)
                    recycle()
            }
        }
    }
}