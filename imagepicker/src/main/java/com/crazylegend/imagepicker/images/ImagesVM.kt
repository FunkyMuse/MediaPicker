package com.crazylegend.imagepicker.images

import android.app.Application
import android.content.ContentUris
import android.database.ContentObserver
import android.provider.MediaStore
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crazylegend.core.context
import com.crazylegend.core.registerObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesVM(application: Application) : AndroidViewModel(application) {

    private val contentResolver get() = context.contentResolver

    private val imagesData = MutableLiveData<List<ImageModel>>()
    val images: LiveData<List<ImageModel>> = imagesData

    private var contentObserver: ContentObserver? = null


    fun loadImages(sortOrder: ImageSortOrder = ImageSortOrder.DATE_ADDED_DESC) {
        viewModelScope.launch {
            imagesData.postValue(queryImages(sortOrder))
            initializeContentObserver(sortOrder)
        }
    }

    private fun initializeContentObserver(sortOrder: ImageSortOrder) {
        if (contentObserver == null) {
            contentObserver = contentResolver.registerObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI) { loadImages(sortOrder) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        contentObserver?.apply {
            contentResolver.unregisterContentObserver(this)
        }
    }

    private suspend fun queryImages(order: ImageSortOrder): List<ImageModel> {
        val images = mutableListOf<ImageModel>()

        val sortOrder = when (order) {
            ImageSortOrder.DATE_ADDED_DESC -> "${MediaStore.Images.Media.DATE_ADDED} DESC"
            ImageSortOrder.DATE_ADDED_ASC -> "${MediaStore.Images.Media.DATE_ADDED} ASC"

            ImageSortOrder.DISPLAY_NAME_DESC -> "${MediaStore.Images.Media.DISPLAY_NAME} DESC"
            ImageSortOrder.DISPLAY_NAME_ASC -> "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

            ImageSortOrder.DATE_MODIFIED_DESC -> "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
            ImageSortOrder.DATE_MODIFIED_ASC -> "${MediaStore.Images.Media.DATE_MODIFIED} ASC"

            ImageSortOrder.SIZE_DESC -> "${MediaStore.Images.Media.SIZE} DESC"
            ImageSortOrder.SIZE_ASC -> "${MediaStore.Images.Media.SIZE} ASC"
        }
        withContext(Dispatchers.IO) {
            val projection =
                    arrayOf(
                            MediaStore.Images.Media._ID,
                            MediaStore.Images.Media.DISPLAY_NAME,
                            MediaStore.Images.Media.DATE_ADDED,
                            MediaStore.Images.Media.DATE_MODIFIED,
                            MediaStore.Images.Media.DESCRIPTION,
                            MediaStore.Images.Media.SIZE,
                            MediaStore.Images.Media.WIDTH,
                            MediaStore.Images.Media.HEIGHT
                    )



            contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder)?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
                val descriptionColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DESCRIPTION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
                val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)


                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getStringOrNull(displayNameColumn)
                    val dateAdded = cursor.getLongOrNull(dateAddedColumn)
                    val dateModified = cursor.getLongOrNull(dateModifiedColumn)
                    val description = cursor.getStringOrNull(descriptionColumn)
                    val size = cursor.getIntOrNull(sizeColumn)
                    val width = cursor.getIntOrNull(widthColumn)
                    val height = cursor.getIntOrNull(heightColumn)

                    val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    val image = ImageModel(id, displayName, dateAdded, contentUri, dateModified, description, size, width, height)
                    images += image
                }
            }
        }
        return images
    }
}