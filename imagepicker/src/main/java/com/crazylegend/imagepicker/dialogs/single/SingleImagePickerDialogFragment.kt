package com.crazylegend.imagepicker.dialogs.single

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.core.abstracts.AbstractDialogFragment
import com.crazylegend.core.gone
import com.crazylegend.core.viewBinding.viewBinding
import com.crazylegend.imagepicker.adapters.single.ImagesAdapter
import com.crazylegend.imagepicker.contracts.SinglePickerContracts
import com.crazylegend.imagepicker.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagePicked


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleImagePickerDialogFragment : AbstractDialogFragment(), SinglePickerContracts {

    override var onImagePicked: onImagePicked? = null
    override val layout: Int
        get() = super.layout

    override val binding by viewBinding(FragmentImagesGalleryLayoutBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val imagesAdapter by lazy {
        ImagesAdapter {
            onImagePicked?.forImage(it)
            dismissAllowingStateLoss()
        }
    }

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
        binding.topIndicator.gone()
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        binding.close.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = imagesAdapter
        }
        imagesVM.images.observe(viewLifecycleOwner) {
            imagesAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onImagePicked = null
    }

}