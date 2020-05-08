package com.crazylegend.imagepicker.dialogs

import android.Manifest
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.core.viewBinding.viewBinding
import com.crazylegend.imagepicker.R
import com.crazylegend.imagepicker.adapter.ImagesAdapter
import com.crazylegend.imagepicker.contracts.SinglePickerContracts
import com.crazylegend.imagepicker.databinding.FragmentGalleryLayoutBinding
import com.crazylegend.imagepicker.images.ImagesVM
import com.crazylegend.imagepicker.listeners.onImagePicked
import com.crazylegend.imagepicker.picker.SingleImagePicker


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class SingleImagePickerDialogFragment : DialogFragment(), SinglePickerContracts {

    companion object {
        var onImagePicked: onImagePicked? = null
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override val binding by viewBinding(FragmentGalleryLayoutBinding::bind)
    override val imagesVM by viewModels<ImagesVM>()
    override val askForStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            imagesVM.loadImages()
        } else {
            Log.e(SingleImagePicker.javaClass.name, "PERMISSION DENIED")
            dismissAllowingStateLoss()
        }
    }

    override val imagesAdapter by lazy {
        ImagesAdapter {
            SingleImagePickerBottomSheetDialog.onImagePicked?.forImage(it)
            dismissAllowingStateLoss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(layout, container, false)
        view.findViewById<AppCompatImageView>(R.id.topIndicator).visibility = View.GONE
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

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