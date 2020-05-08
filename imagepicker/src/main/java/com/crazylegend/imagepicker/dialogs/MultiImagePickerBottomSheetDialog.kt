package com.crazylegend.imagepicker.dialogs

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.util.keyIterator
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.core.viewBinding.viewBinding
import com.crazylegend.imagepicker.adapter.ImagesMultiSelectAdapter
import com.crazylegend.imagepicker.consts.LIST_STATE
import com.crazylegend.imagepicker.contracts.MultiPickerContracts
import com.crazylegend.imagepicker.databinding.FragmentGalleryLayoutMultiBinding
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked
import com.crazylegend.imagepicker.picker.SingleImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiImagePickerBottomSheetDialog : BottomSheetDialogFragment(),
        MultiPickerContracts {

    companion object {
        var onImagesPicked: onImagesPicked? = null
    }


    override val binding by viewBinding(FragmentGalleryLayoutMultiBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    imagesVM.loadImages()
                } else {
                    Log.e(SingleImagePicker.javaClass.name, "PERMISSION DENIED")
                    dismissAllowingStateLoss()
                }
            }

    override val imagesAdapter by lazy {
        ImagesMultiSelectAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.getIntegerArrayList(LIST_STATE)?.asSequence()?.forEach { imagesAdapter.selectedPositions.put(it, true) }
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = imagesAdapter
        }

        imagesVM.images.observe(viewLifecycleOwner) {
            imagesAdapter.submitList(it)
        }


        binding.doneButton.setOnClickListener {
            val imagesList = imagesVM.images.value ?: emptyList()
            val pickedList = mutableListOf<ImageModel>()
            imagesAdapter.selectedPositions.keyIterator().asSequence().forEach {
                pickedList += imagesList[it]
            }
            onImagesPicked?.onImagesPicked(pickedList)
            dismissAllowingStateLoss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val positionList = imagesAdapter.selectedPositions.keyIterator().asSequence().map { it }.toList()
        outState.putIntegerArrayList(LIST_STATE, ArrayList(positionList))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onImagesPicked = null
    }
}