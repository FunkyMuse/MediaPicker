package com.crazylegend.core.dto

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
 * Created by crazy on 5/11/20 to long live and prosper !
 */

@Parcelize
open class BaseCursorModel(
        open val id: Long,
        open val displayName: String?,
        open val dateAdded: Long?,
        open val contentUri: Uri,
        open val dateModified: Long?,
        open val description: String?,
        open val size: Int?,
        open val width: Int?,
        open val height: Int?
) : Parcelable {


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
                      legacyKind:Int = MediaStore.Video.Thumbnails.MICRO_KIND,
                      options: BitmapFactory.Options? = null): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver.loadThumbnail(contentUri, customSize, null)
        } else {
            MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, legacyKind,
                    options ?: BitmapFactory.Options())
        }
    }

    val extension get() = displayName?.substringAfterLast(".")

    val addedDateAsDate
        get() = dateAdded?.let {
            Date(TimeUnit.SECONDS.toMillis(it))
        }
    val dateModifiedAsDate
        get() = dateModified?.let {
            Date(TimeUnit.SECONDS.toMillis(it))
        }
}