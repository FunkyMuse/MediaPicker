package com.crazylegend.imagepicker.dialogs.single

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
import com.crazylegend.core.adapters.single.SingleAdapter
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.core.modifiers.base.BaseSinglePickerModifier
import com.crazylegend.extensions.viewBinding
import com.crazylegend.imagepicker.contracts.SinglePickerContracts
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagePicked
import com.crazylegend.imagepicker.pickers.SingleImagePicker


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleImagePickerBottomSheetDialog : AbstractBottomSheetDialogFragment(),
    SinglePickerContracts {

    override val layout: Int
        get() = super.layout
    override var onImagePicked: onImagePicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val modifier: BaseSinglePickerModifier?
        get() = arguments?.getParcelable(modifierTag)
    var extensions: Array<String>? = arrayOf()

    override val singleAdapter by lazy {
        SingleAdapter(modifier?.viewHolderPlaceholderModifier) {
            val image = it as ImageModel
            setFragmentResult(
                SingleImagePicker.SINGLE_IMAGE_REQUEST_KEY,
                bundleOf(SingleImagePicker.ON_SINGLE_IMAGE_PICK_KEY to image)
            )
            onImagePicked?.forImage(image)
            dismissAllowingStateLoss()
        }
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            askForStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            askForStoragePermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
        setupUIForSinglePicker(
            binding.gallery,
            singleAdapter,
            binding.title,
            binding.loadingIndicator,
            modifier?.loadingIndicatorTint,
            ::applyTitleModifications
        )
        imagesVM.images.observe(viewLifecycleOwner) {
            setupList(singleAdapter, it, binding.noContentText, modifier?.noContentTextModifier)
        }
        handleUIIndicator(imagesVM.loadingIndicator, binding.loadingIndicator)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        onImagePicked = null
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun addModifier(modifier: BaseSinglePickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

}