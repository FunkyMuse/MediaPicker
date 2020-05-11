package com.crazylegend.core.dto

import android.net.Uri
import android.os.Parcelable
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