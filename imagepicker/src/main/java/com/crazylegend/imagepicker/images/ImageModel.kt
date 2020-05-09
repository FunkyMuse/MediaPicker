package com.crazylegend.imagepicker.images

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Simple data class to hold information about an image included in the device's MediaStore.
 */

@Parcelize
data class ImageModel(
        val id: Long,
        val displayName: String?,
        val dateAdded: Long?,
        val contentUri: Uri,
        val dateModified: Long?,
        val description: String?,
        val size: Int?,
        val width: Int?,
        val height: Int?
) : Parcelable {

    val addedDateAsDate
        get() = dateAdded?.let {
            Date(TimeUnit.SECONDS.toMillis(it))
        }
    val dateModifiedAsDate
        get() = dateModified?.let {
            Date(TimeUnit.SECONDS.toMillis(it))
        }

}