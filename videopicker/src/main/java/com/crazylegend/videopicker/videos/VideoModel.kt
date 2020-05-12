package com.crazylegend.videopicker.videos

import android.net.Uri
import android.os.Parcelable
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


}