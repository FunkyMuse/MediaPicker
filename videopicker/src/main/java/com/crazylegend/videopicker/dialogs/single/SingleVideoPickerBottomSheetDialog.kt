package com.crazylegend.videopicker.dialogs.single

import android.Manifest
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
import com.crazylegend.videopicker.contracts.SinglePickerContracts
import com.crazylegend.videopicker.listeners.onVideoPicked
import com.crazylegend.videopicker.pickers.SingleVideoPicker
import com.crazylegend.videopicker.videos.VideoModel
import com.crazylegend.videopicker.videos.VideosVM


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleVideoPickerBottomSheetDialog : AbstractBottomSheetDialogFragment(), SinglePickerContracts {

    override val layout: Int
        get() = super.layout
    override var onVideoPicked: onVideoPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutBinding::bind)
    override val videosVM by viewModels<VideosVM>()
    override val modifier: BaseSinglePickerModifier? get() = arguments?.getParcelable(modifierTag)

    override val singleAdapter by lazy {
        SingleAdapter(modifier?.viewHolderPlaceholderModifier) {
            setFragmentResult(SingleVideoPicker.SINGLE_VIDEO_REQUEST_KEY, bundleOf(SingleVideoPicker.ON_SINGLE_VIDEO_PICK_KEY to it as VideoModel))
            onVideoPicked?.forVideo(it)
            dismissAllowingStateLoss()
        }
    }

    override val askForStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            videosVM.loadVideos()
        } else {
            Log.e(errorTag, "PERMISSION DENIED")
            dismissAllowingStateLoss()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        setupUIForSinglePicker(binding.gallery, singleAdapter, binding.title, binding.loadingIndicator, modifier?.loadingIndicatorTint, ::applyTitleModifications)
        videosVM.videos.observe(viewLifecycleOwner) {
            setupList(singleAdapter, it, binding.noContentText, modifier?.noContentTextModifier)
        }
        handleUIIndicator(videosVM.loadingIndicator, binding.loadingIndicator)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        onVideoPicked = null
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun addModifier(modifier: BaseSinglePickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

}