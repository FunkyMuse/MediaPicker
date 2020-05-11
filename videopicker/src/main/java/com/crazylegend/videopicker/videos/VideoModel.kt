package com.crazylegend.videopicker.videos

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Size
import com.crazylegend.core.dto.BaseCursorModel
import kotlinx.android.parcel.Parcelize


/**
 * Simple data class to hold information about an image included in the device's MediaStore.
 */

@Parcelize
data class VideoModel(
        override val id: Long,
        override val displayName: String?,
        override val dateAdded: Long?,
        override val contentUri: Uri,
        override val dateModified: Long?,
        override val description: String?,
        override val size: Int?,
        override val width: Int?,
        override val height: Int?,
        val resolution: String?,
        val private: Int?,
        val artist: String?,
        val album: String?,
        val category: String?,
        val tags: String?,
        val language: String?,
        val bookmark: Int?
) : BaseCursorModel(id, displayName, dateAdded, contentUri, dateModified, description, size, width, height), Parcelable {


    /**
     * Or just use Glide with the contentUri
     * @param contentResolver ContentResolver
     * @param customSize Size
     * @param options Options?
     * @return Bitmap?
     */
    @Suppress("DEPRECATION")
    fun loadThumbnail(contentResolver: ContentResolver,
                      customSize: Size = Size(size ?: 150, size ?: 150),
                      options: BitmapFactory.Options? = null): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver.loadThumbnail(contentUri, customSize, null)
        } else {
            MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Video.Thumbnails.MICRO_KIND,
                    options ?: BitmapFactory.Options())
        }
    }
}