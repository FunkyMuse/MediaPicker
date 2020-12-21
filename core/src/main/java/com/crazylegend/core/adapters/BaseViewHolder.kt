package com.crazylegend.core.adapters

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.crazylegend.core.R
import com.crazylegend.core.loadImage
import com.crazylegend.core.modifiers.multi.SelectIconModifier
import com.crazylegend.core.modifiers.single.ImageModifier


/**
 * Created by crazy on 5/16/20 to long live and prosper !
 */
open class BaseViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun loadImage(image: AppCompatImageView, contentUri: Uri, viewHolderPlaceholderModifier: ImageModifier?) {
        image.loadImage(contentUri) {
            if (viewHolderPlaceholderModifier != null) {
                loadPlaceHolders(viewHolderPlaceholderModifier, image)
            }
        }
    }

    /**
     * Or just use Glide with the contentUri
     * @param contentResolver ContentResolver
     * @param customSize Size
     * @param options Options?
     * @return Bitmap?
     */
    @Suppress("DEPRECATION")
    fun loadThumbnail(contentResolver: ContentResolver,
                      contentUri: Uri,
                      id: Long,
                      customSize: Size = Size(350, 350),
                      legacyKind: Int = MediaStore.Video.Thumbnails.MICRO_KIND,
                      options: BitmapFactory.Options? = null): Bitmap? {
        return tryOrNull {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver.loadThumbnail(contentUri, customSize, null)
            } else {
                MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, legacyKind,
                        options ?: BitmapFactory.Options())
            }
        }
    }

    private fun tryOrNull(function: () -> Bitmap): Bitmap? {
        return try {
            function()
        } catch (e: Exception) {
            null
        }
    }

    fun loadPlaceHolders(viewHolderPlaceholderModifier: ImageModifier?, image: AppCompatImageView) {
        if (viewHolderPlaceholderModifier != null) {
            viewHolderPlaceholderModifier.resID = viewHolderPlaceholderModifier.resID
                    ?: R.drawable.ic_image
            viewHolderPlaceholderModifier.applyImageParamsConstraintLayout(image)
        } else {
            image.setImageResource(R.drawable.ic_image)
        }
    }

    fun setupUnselectedImage(selection: AppCompatImageView, unSelectedIconModifier: SelectIconModifier?) {
        val resID = unSelectedIconModifier?.resID ?: R.drawable.ic_unchecked_default
        unSelectedIconModifier?.applyImageParams(selection, resID)
    }

    fun setupSelectedImage(selection: AppCompatImageView, selectIconModifier: SelectIconModifier?) {
        val resID = selectIconModifier?.resID ?: R.drawable.ic_checked_default
        selectIconModifier?.applyImageParams(selection, resID)
    }
}