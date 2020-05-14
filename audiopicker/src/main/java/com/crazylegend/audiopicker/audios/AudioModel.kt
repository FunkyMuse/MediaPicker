package com.crazylegend.audiopicker.audios

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Size
import com.crazylegend.core.dto.BaseCursorModel
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
                      val year: Int?, var thumbnail:Bitmap? = null) :
        BaseCursorModel(id, displayName, dateAdded, contentUri, dateModified, description, size, width, height), Parcelable {

    @IgnoredOnParcel
    var hasTriedToLoadTumbnail = false

    @Suppress("DEPRECATION")
    fun loadThumbnailScoped(contentResolver: ContentResolver,
                            coroutineScope: CoroutineScope,
                            customSize: Size = Size(size ?: 150, size ?: 150),
                            legacyKind: Int = MediaStore.Video.Thumbnails.MICRO_KIND,
                            options: BitmapFactory.Options? = null, onBitmap: (Bitmap?) -> Unit) {

        coroutineScope.launch(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (thumbnail == null && !hasTriedToLoadTumbnail){
                    withContext(Dispatchers.Main){
                        onBitmap(tryOrNull {
                            val thumb = contentResolver.loadThumbnail(contentUri, customSize, null)
                            thumbnail= thumb
                            thumb
                        })
                        hasTriedToLoadTumbnail = true
                    }
                } else {
                    withContext(Dispatchers.Main){
                        onBitmap(thumbnail)
                    }
                }
            } else {
                if (thumbnail== null){
                    withContext(Dispatchers.Main){
                        onBitmap(tryOrNull {
                            val thumb =  MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, legacyKind,
                                    options ?: BitmapFactory.Options())
                            thumbnail= thumb
                            thumb })
                    }
                } else {
                    withContext(Dispatchers.Main){
                        onBitmap(thumbnail)
                    }
                }
            }
        }

    }
}