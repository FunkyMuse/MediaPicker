package com.crazylegend.videopicker.videos

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Size
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Simple data class to hold information about an image included in the device's MediaStore.
 */

@Parcelize
data class VideoModel(
        val id: Long,
        val displayName: String?,
        val dateAdded: Long?,
        val contentUri: Uri,
        val dateModified: Long?,
        val description: String?,
        val size: Int?,
        val width: Int?,
        val height: Int?,
        val resolution: String?,
        val private: Int?,
        val artist: String?,
        val album: String?,
        val category: String?,
        val tags: String?,
        val language: String?,
        val bookmark: Int?
) : Parcelable {

    val getVideoExtension get() = displayName?.substringAfterLast(".")

    val addedDateAsDate
        get() = dateAdded?.let {
            Date(TimeUnit.SECONDS.toMillis(it))
        }
    val dateModifiedAsDate
        get() = dateModified?.let {
            Date(TimeUnit.SECONDS.toMillis(it))
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