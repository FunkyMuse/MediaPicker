package com.crazylegend.imagepicker.dialogs.multi

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
import com.crazylegend.core.R
import com.crazylegend.core.abstracts.AbstractDialogFragment
import com.crazylegend.core.adapters.multi.MultiSelectAdapter
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.extensions.viewBinding
import com.crazylegend.imagepicker.consts.LIST_STATE
import com.crazylegend.imagepicker.contracts.MultiPickerContracts
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiImagePickerDialogFragment : AbstractDialogFragment(R.layout.fragment_images_gallery_layout_multi),
        MultiPickerContracts {

    override var onImagesPicked: onImagesPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutMultiBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val multiSelectAdapter by lazy {
        MultiSelectAdapter(modifier)
    }
    override val modifier: MultiPickerModifier?
        get() = arguments?.getParcelable(modifierTag)

    override val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    imagesVM.loadImages()
                } else {
                    Log.e(errorTag, "PERMISSION DENIED")
                    dismissAllowingStateLoss()
                }
            }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        setupUIForMultiPicker(binding.topIndicator, savedInstanceState, LIST_STATE, multiSelectAdapter.selectedPositions,
                binding.gallery, multiSelectAdapter, binding.doneButton, binding.title,
                ::applyDoneButtonModifications, ::applyTitleModifications)

        imagesVM.images.observe(viewLifecycleOwner) {
            multiSelectAdapter.submitList(it)
        }
        handleUIIndicator(imagesVM.loadingIndicator, binding.loadingIndicator)

        binding.doneButton.setOnClickListener {
            onValuesPicked(multiSelectAdapter.selectedPositions, imagesVM.images.value ?: emptyList()) { list ->
                onImagesPicked?.onImagesPicked(list)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveSelectedPositionsState(multiSelectAdapter.selectedPositions, outState, LIST_STATE)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        onImagesPicked = null
    }

    override fun addModifier(modifier: MultiPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }
    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun applyDoneButtonModifications(doneButton: MaterialButton) {
        modifier?.doneButtonModifier?.applyImageParams(doneButton)
    }
}