package com.crazylegend.imagepicker.dialogs.multi

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.core.util.keyIterator
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.core.abstracts.AbstractBottomSheetDialogFragment
import com.crazylegend.core.adapters.multi.MultiSelectAdapter
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.core.modifiers.multi.MultiPickerModifier
import com.crazylegend.extensions.viewBinding
import com.crazylegend.imagepicker.consts.LIST_STATE
import com.crazylegend.imagepicker.contracts.MultiPickerContracts
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagesPicked

import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiImagePickerBottomSheetDialog : AbstractBottomSheetDialogFragment(),
        MultiPickerContracts {


    override val layout: Int
        get() = super.layout
    override var onImagesPicked: onImagesPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutMultiBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val modifier: MultiPickerModifier?
        get() = arguments?.getParcelable(modifierTag)

    override val multiSelectAdapter by lazy {
        MultiSelectAdapter(modifier)
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
        savedInstanceState?.getIntegerArrayList(LIST_STATE)?.asSequence()?.forEach { multiSelectAdapter.selectedPositions.put(it, true) }
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = multiSelectAdapter
        }

        imagesVM.images.observe(viewLifecycleOwner) {
            multiSelectAdapter.submitList(it)
        }

        applyDoneButtonModifications(binding.doneButton)
        applyTitleModifications(binding.title)

        binding.doneButton.setOnClickListener {
            val imagesList = imagesVM.images.value ?: emptyList()
            val pickedList = mutableListOf<ImageModel>()
            multiSelectAdapter.selectedPositions.keyIterator().asSequence().forEach {
                pickedList += imagesList[it]
            }
            onImagesPicked?.onImagesPicked(pickedList)
            dismissAllowingStateLoss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val positionList = multiSelectAdapter.selectedPositions.keyIterator().asSequence().map { it }.toList()
        outState.putIntegerArrayList(LIST_STATE, ArrayList(positionList))
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