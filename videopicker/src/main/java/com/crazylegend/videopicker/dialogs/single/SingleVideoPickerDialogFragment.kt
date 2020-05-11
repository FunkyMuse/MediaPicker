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
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.core.abstracts.AbstractDialogFragment
import com.crazylegend.core.gone
import com.crazylegend.core.viewBinding.viewBinding
import com.crazylegend.videopicker.adapters.single.VideoAdapter
import com.crazylegend.videopicker.contracts.SinglePickerContracts
import com.crazylegend.videopicker.databinding.FragmentVideoGalleryLayoutBinding
import com.crazylegend.videopicker.listeners.onVideoPicked
import com.crazylegend.videopicker.modifiers.SingleVideoPickerModifier
import com.crazylegend.videopicker.videos.VideosVM


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleVideoPickerDialogFragment : AbstractDialogFragment(), SinglePickerContracts {

    override val layout: Int
        get() = super.layout
    override var onVideoPicked: onVideoPicked? = null
    override val binding by viewBinding(FragmentVideoGalleryLayoutBinding::bind)
    override val videosVM by viewModels<VideosVM>()
    override val modifier: SingleVideoPickerModifier?
        get() = arguments?.getParcelable(modifierTag)

    override val videoAdapter by lazy {
        VideoAdapter {
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
        binding.topIndicator.gone()
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        modifier?.closeButtonModifier?.applyImageParams(binding.close)
        applyTitleModifications(binding.title)

        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = videoAdapter
        }

        binding.close.setOnClickListener {
            dismissAllowingStateLoss()
        }
        
        videosVM.videos.observe(viewLifecycleOwner) {
            videoAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onVideoPicked = null
    }

    override fun applyTitleModifications(appCompatTextView: AppCompatTextView) {
        modifier?.titleTextModifier?.applyTextParams(appCompatTextView)
    }

    override fun addModifier(modifier: SingleVideoPickerModifier) {
        arguments = bundleOf(modifierTag to modifier)
    }

}