package com.crazylegend.imagepicker.images

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crazylegend.core.abstracts.AbstractAVM
import com.crazylegend.core.sorting.SortOrder
import com.crazylegend.extensions.getSafeColumn
import com.crazylegend.extensions.registerObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class ImagesVM(application: Application) : AbstractAVM(application) {

    private val imagesData = MutableLiveData<List<ImageModel>>()
    val images: LiveData<List<ImageModel>> = imagesData


    fun loadImages(sortOrder: SortOrder = SortOrder.DATE_ADDED_DESC) {
        if (canLoad) {
            viewModelScope.launch {
                imagesData.postValue(queryImages(sortOrder))
                initializeContentObserver(sortOrder)
            }
        }
    }

    private fun initializeContentObserver(sortOrder: SortOrder) {
        if (contentObserver == null) {
            contentObserver = contentResolver.registerObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                canLoad = true
                loadImages(sortOrder)
            }
        }
    }

    private suspend fun queryImages(order: SortOrder): List<ImageModel> {
        loadingIndicatorData.value = true

        val images = mutableListOf<ImageModel>()

        val sortOrder = when (order) {
            SortOrder.DATE_ADDED_DESC -> "${MediaStore.Images.Media.DATE_ADDED} DESC"
            SortOrder.DATE_ADDED_ASC -> "${MediaStore.Images.Media.DATE_ADDED} ASC"

            SortOrder.DISPLAY_NAME_DESC -> "${MediaStore.Images.Media.DISPLAY_NAME} DESC"
            SortOrder.DISPLAY_NAME_ASC -> "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

            SortOrder.DATE_MODIFIED_DESC -> "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
            SortOrder.DATE_MODIFIED_ASC -> "${MediaStore.Images.Media.DATE_MODIFIED} ASC"

            SortOrder.SIZE_DESC -> "${MediaStore.Images.Media.SIZE} DESC"
            SortOrder.SIZE_ASC -> "${MediaStore.Images.Media.SIZE} ASC"
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
                val dateAddedColumn = cursor.getSafeColumn(MediaStore.Images.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getSafeColumn(MediaStore.Images.Media.DATE_MODIFIED)
                val descriptionColumn = cursor.getSafeColumn(MediaStore.Images.Media.DESCRIPTION)
                val sizeColumn = cursor.getSafeColumn(MediaStore.Images.Media.SIZE)
                val widthColumn = cursor.getSafeColumn(MediaStore.Images.Media.WIDTH)
                val heightColumn = cursor.getSafeColumn(MediaStore.Images.Media.HEIGHT)


                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getStringOrNull(displayNameColumn)
                    val dateAdded = dateAddedColumn?.let { cursor.getLongOrNull(it) }
                    val dateModified = dateModifiedColumn?.let { cursor.getLongOrNull(it) }
                    val description = descriptionColumn?.let { cursor.getStringOrNull(it) }
                    val size = sizeColumn?.let { cursor.getIntOrNull(it) }
                    val width = widthColumn?.let { cursor.getIntOrNull(it) }
                    val height = heightColumn?.let { cursor.getIntOrNull(it) }

                    val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    val image = ImageModel(id, displayName, dateAdded, contentUri, dateModified, description, size, width, height)
                    image.isSelected = imagesData.value?.find { it.id == image.id }?.isSelected ?: false
                    images += image
                }
            }
        }
        canLoad = false
        loadingIndicatorData.value = false
        return images
    }
}