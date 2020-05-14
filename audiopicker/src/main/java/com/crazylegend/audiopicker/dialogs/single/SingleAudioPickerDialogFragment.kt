package com.crazylegend.audiopicker.dialogs.single

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
import com.crazylegend.audiopicker.adapters.single.AudioSingleAdapter
import com.crazylegend.audiopicker.audios.AudiosVM
import com.crazylegend.audiopicker.contracts.SinglePickerContracts
import com.crazylegend.audiopicker.listeners.onAudioPicked
import com.crazylegend.audiopicker.modifiers.SingleAudioPickerModifier
import com.crazylegend.core.R
import com.crazylegend.core.abstracts.AbstractDialogFragment
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.extensions.viewBinding


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleAudioPickerDialogFragment : AbstractDialogFragment(R.layout.fragment_images_gallery_layout), SinglePickerContracts {
    override val layout: Int
        get() = super.layout
    override var onAudioPicked: onAudioPicked?=null
    override val binding by viewBinding(FragmentImagesGalleryLayoutBinding::bind)
    override val audiosVM by viewModels<AudiosVM>()
    override val modifier: SingleAudioPickerModifier? get() = arguments?.getParcelable(modifierTag)


    override val singleAdapter by lazy {
        AudioSingleAdapter(modifier?.viewHolderPlaceholderModifier, modifier?.viewHolderTitleModifier) {
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
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        setupUIForSinglePicker(binding.topIndicator, binding.gallery, singleAdapter, binding.title, binding.close,
                ::applyTitleModifications){
            modifier?.singlePickerModifier?.closeButtonModifier?.applyImageParamsRelativeLayout(it)
        }
        audiosVM.audio.observe(viewLifecycleOwner) {
            singleAdapter.submitList(it)
        }
        handleUIIndicator(audiosVM.loadingIndicator, binding.loadingIndicator)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycleBitmaps()
        onAudioPicked = null
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.singlePickerModifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun addModifier(modifier: SingleAudioPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

    override fun recycleBitmaps() {
        singleAdapter.currentList.asSequence().forEach {
            it?.thumbnail?.apply {
                if (!isRecycled)
                    recycle()
            }
        }
    }
}