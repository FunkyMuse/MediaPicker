package com.crazylegend.videopicker.dialogs.multi

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
import com.crazylegend.core.abstracts.AbstractBottomSheetDialogFragment
import com.crazylegend.core.adapters.multi.MultiSelectAdapter
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutMultiBinding
import com.crazylegend.core.modifiers.base.BaseMultiPickerModifier
import com.crazylegend.extensions.viewBinding
import com.crazylegend.videopicker.contracts.MultiPickerContracts
import com.crazylegend.videopicker.listeners.onVideosPicked
import com.crazylegend.videopicker.videos.VideoModel
import com.crazylegend.videopicker.videos.VideosVM
import com.google.android.material.button.MaterialButton


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class MultiVideoPickerBottomSheetDialog : AbstractBottomSheetDialogFragment(),
        MultiPickerContracts {


    override val layout: Int
        get() = super.layout
    override var onVideosPicked: onVideosPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutMultiBinding::bind)
    override val videosVM by viewModels<VideosVM>()
    override val modifier: BaseMultiPickerModifier?
        get() = arguments?.getParcelable(modifierTag)
    override val multiSelectAdapter by lazy {
        MultiSelectAdapter(modifier)
    }
    override val askForStoragePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    videosVM.loadVideos()
                } else {
                    Log.e(errorTag, "PERMISSION DENIED")
                    dismissAllowingStateLoss()
                }
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        setupUIForMultiPicker(
                binding.gallery, multiSelectAdapter, binding.doneButton, binding.title, binding.loadingIndicator, modifier?.loadingIndicatorTint,
                ::applyDoneButtonModifications, ::applyTitleModifications)

        videosVM.videos.observe(viewLifecycleOwner) {
            multiSelectAdapter.submitList(it)
        }
        handleUIIndicator(videosVM.loadingIndicator, binding.loadingIndicator)


        binding.doneButton.setOnClickListener {
            onVideosPicked?.onVideosPicked(multiSelectAdapter.currentList.filter { it.isSelected } as? List<VideoModel> ?: emptyList())
            dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onVideosPicked = null
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