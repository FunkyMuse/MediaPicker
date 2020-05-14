package com.crazylegend.audiopicker.audios

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import com.crazylegend.core.dto.BaseCursorModel
import kotlinx.android.parcel.Parcelize


/**
 * Created by crazy on 5/12/20 to long live and prosper !
 */

@Parcelize
data class AudioModel(override val id: Long,
                      override val displayName: String?,
                      override val dateAdded: Long?,
                      override val contentUri: Uri,
                      override val dateModified: Long?,
                      override val description: String?,
                      override val size: Int?,
                      override val width: Int?,
                      override val height: Int?,
                      val album: String?,
                      val composer: String?,
                      val artist: String?,
                      val notification: Boolean,
                      val alarm: Boolean,
                      val ringtone: Boolean,
                      val track: Int?,
                      val year: Int?,
                      var thumbnail: Bitmap? = null) :
        BaseCursorModel(id, displayName, dateAdded, contentUri, dateModified, description, size, width, height), Parcelable