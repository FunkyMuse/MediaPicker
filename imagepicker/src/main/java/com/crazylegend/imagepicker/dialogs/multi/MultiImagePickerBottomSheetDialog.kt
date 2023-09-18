package com.crazylegend.imagepicker.dialogs.multi

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
import com.crazylegend.core.abstracts.AbstractBottomSheetDialogFragment
import com.crazylegend.core.adapters.multi.MultiSelectAdapter
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier

import com.crazylegend.extensions.viewBinding
import com.crazylegend.imagepicker.contracts.MultiPickerContracts
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked
import com.crazylegend.imagepicker.pickers.MultiImagePicker
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiImagePickerBottomSheetDialog : AbstractBottomSheetDialogFragment(),
    MultiPickerContracts {

    var extensions: Array<String>? = arrayOf()

    override val layout: Int
        get() = super.layout
    override var onImagesPicked: onImagesPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutMultiBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val modifier: BaseMultiPickerModifier?
        get() = arguments?.getParcelable(modifierTag)

    override val multiSelectAdapter by lazy {
        MultiSelectAdapter(modifier)
    }
    override val askForStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                imagesVM.loadImages(extensions = extensions)
            } else {
                Log.e(errorTag, "PERMISSION DENIED")
                dismissAllowingStateLoss()
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            askForStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            askForStoragePermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }

        setupUIForMultiPicker(
            binding.gallery,
            multiSelectAdapter,
            binding.doneButton,
            binding.title,
            binding.loadingIndicator,
            modifier?.loadingIndicatorTint,
            ::applyDoneButtonModifications,
            ::applyTitleModifications
        )

        imagesVM.images.observe(viewLifecycleOwner) {
            setupList(
                multiSelectAdapter,
                it,
                binding.noContentText,
                modifier?.noContentTextModifier
            )
        }
        handleUIIndicator(imagesVM.loadingIndicator, binding.loadingIndicator)


        binding.doneButton.setOnClickListener {
            val imagesList =
                multiSelectAdapter.currentList.filter { it.isSelected } as? List<ImageModel>
                    ?: emptyList()
            setFragmentResult(
                MultiImagePicker.MULTI_IMAGE_REQUEST_KEY,
                bundleOf(MultiImagePicker.ON_MULTI_IMAGE_PICK_KEY to imagesList)
            )
            onImagesPicked?.onImagesPicked(imagesList)
            dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onImagesPicked = null
    }

    override fun addModifier(modifier: BaseMultiPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun applyDoneButtonModifications(doneButton: MaterialButton) {
        modifier?.doneButtonModifier?.applyImageParams(doneButton)
    }
}