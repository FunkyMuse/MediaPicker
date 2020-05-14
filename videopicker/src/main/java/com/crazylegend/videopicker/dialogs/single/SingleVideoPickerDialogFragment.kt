package com.crazylegend.videopicker.dialogs.single

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
import com.crazylegend.core.adapters.single.SingleAdapter
import com.crazylegend.core.databinding.FragmentImagesGalleryLayoutBinding
import com.crazylegend.core.modifiers.single.SinglePickerModifier
import com.crazylegend.extensions.viewBinding
import com.crazylegend.videopicker.contracts.SinglePickerContracts
import com.crazylegend.videopicker.listeners.onVideoPicked
import com.crazylegend.videopicker.videos.VideoModel
import com.crazylegend.videopicker.videos.VideosVM


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleVideoPickerDialogFragment : AbstractDialogFragment(R.layout.fragment_images_gallery_layout), SinglePickerContracts {

    override var onVideoPicked: onVideoPicked? = null
    override val binding by viewBinding(FragmentImagesGalleryLayoutBinding::bind)
    override val videosVM by viewModels<VideosVM>()
    override val modifier: SinglePickerModifier?
        get() = arguments?.getParcelable(modifierTag)


    override val singleAdapter by lazy {
        SingleAdapter {
            onVideoPicked?.forVideo(it as VideoModel)
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
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        setupUIForSinglePicker(binding.topIndicator, binding.gallery, singleAdapter, binding.title, binding.close,
                ::applyTitleModifications){
            modifier?.closeButtonModifier?.applyImageParamsRelativeLayout(it)
        }
        
        videosVM.videos.observe(viewLifecycleOwner) {
            singleAdapter.submitList(it)
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

    override fun addModifier(modifier: SinglePickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

}