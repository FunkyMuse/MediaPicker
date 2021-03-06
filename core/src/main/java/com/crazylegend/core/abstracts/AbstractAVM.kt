package com.crazylegend.core.abstracts

import android.app.Application
import android.content.ContentResolver
import android.database.ContentObserver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.crazylegend.core.context


/**
 * Created by crazy on 5/14/20 to long live and prosper !
 */
abstract class AbstractAVM(application: Application,
                           private val stateHandle: SavedStateHandle) : AndroidViewModel(application) {

    companion object {
        private const val CAN_LOAD_KEY = "canLoad"
    }

    protected val contentResolver get() = context.contentResolver
    protected var contentObserver: ContentObserver? = null

    protected val loadingIndicatorData = MutableLiveData<Boolean>()
    val loadingIndicator: LiveData<Boolean> = loadingIndicatorData

    /**
     * Using this instead of event since it serves the same purpose thus it's needed here
     */
    protected val canLoad get() = stateHandle[CAN_LOAD_KEY] ?: true

    protected fun setCanLoad() {
        stateHandle[CAN_LOAD_KEY] = true
    }

    protected fun setCanNotLoad() {
        stateHandle[CAN_LOAD_KEY] = false
    }

    override fun onCleared() {
        super.onCleared()
        contentObserver?.apply {
            contentResolver.unregisterContentObserver(this)
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
}