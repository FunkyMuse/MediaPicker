package com.crazylegend.videopicker.videos

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
internal class VideosVM(application: Application) : AbstractAVM(application) {

    private val videoData = MutableLiveData<List<VideoModel>>()
    val videos: LiveData<List<VideoModel>> = videoData


    fun loadVideos(sortOrder: SortOrder = SortOrder.DATE_ADDED_DESC) {
        if (canLoad) {
            viewModelScope.launch {
                videoData.postValue(queryVideos(sortOrder))
                initializeContentObserver(sortOrder)
            }
        }
    }

    private fun initializeContentObserver(sortOrder: SortOrder) {
        if (contentObserver == null) {
            contentObserver = contentResolver.registerObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                canLoad = true
                loadVideos(sortOrder)
            }
        }
    }

    private suspend fun queryVideos(order: SortOrder): List<VideoModel> {
        loadingIndicatorData.value = true

        val video = mutableListOf<VideoModel>()

        val sortOrder = when (order) {
            SortOrder.DATE_ADDED_DESC -> "${MediaStore.Video.Media.DATE_ADDED} DESC"
            SortOrder.DATE_ADDED_ASC -> "${MediaStore.Video.Media.DATE_ADDED} ASC"

            SortOrder.DISPLAY_NAME_DESC -> "${MediaStore.Video.Media.DISPLAY_NAME} DESC"
            SortOrder.DISPLAY_NAME_ASC -> "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

            SortOrder.DATE_MODIFIED_DESC -> "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
            SortOrder.DATE_MODIFIED_ASC -> "${MediaStore.Video.Media.DATE_MODIFIED} ASC"

            SortOrder.SIZE_DESC -> "${MediaStore.Video.Media.SIZE} DESC"
            SortOrder.SIZE_ASC -> "${MediaStore.Video.Media.SIZE} ASC"
        }
        withContext(Dispatchers.IO) {
            val projection =
                    arrayOf(
                            MediaStore.Video.Media._ID,
                            MediaStore.Video.Media.DISPLAY_NAME,
                            MediaStore.Video.Media.RESOLUTION,
                            MediaStore.Video.Media.IS_PRIVATE,
                            MediaStore.Video.Media.DATE_ADDED,
                            MediaStore.Video.Media.DATE_MODIFIED,
                            MediaStore.Video.Media.DESCRIPTION,
                            MediaStore.Video.Media.SIZE,
                            MediaStore.Video.Media.WIDTH,
                            MediaStore.Video.Media.HEIGHT
                    )

            contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder)?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val resolutionColumn = cursor.getSafeColumn(MediaStore.Video.Media.RESOLUTION)
                val isPrivateColumn = cursor.getSafeColumn(MediaStore.Video.Media.IS_PRIVATE)
                val tagsColumn = cursor.getSafeColumn(MediaStore.Video.Media.TAGS)
                val dateAddedColumn = cursor.getSafeColumn(MediaStore.Video.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getSafeColumn(MediaStore.Video.Media.DATE_MODIFIED)
                val descriptionColumn = cursor.getSafeColumn(MediaStore.Video.Media.DESCRIPTION)
                val sizeColumn = cursor.getSafeColumn(MediaStore.Video.Media.SIZE)
                val widthColumn = cursor.getSafeColumn(MediaStore.Video.Media.WIDTH)
                val heightColumn = cursor.getSafeColumn(MediaStore.Video.Media.HEIGHT)


                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getStringOrNull(displayNameColumn)
                    val resolution = resolutionColumn?.let { cursor.getStringOrNull(it) }
                    val isPrivate = isPrivateColumn?.let { cursor.getIntOrNull(it) }
                    val tags = tagsColumn?.let { cursor.getStringOrNull(it) }
                    val dateAdded = dateAddedColumn?.let { cursor.getLongOrNull(it) }
                    val dateModified = dateModifiedColumn?.let { cursor.getLongOrNull(it) }
                    val description = descriptionColumn?.let { cursor.getStringOrNull(it) }
                    val size = sizeColumn?.let { cursor.getIntOrNull(it) }
                    val width = widthColumn?.let { cursor.getIntOrNull(it) }
                    val height = heightColumn?.let { cursor.getIntOrNull(it) }

                    val contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    val videoModel = VideoModel(id, displayName, dateAdded, contentUri, dateModified, description, size, width, height,
                            resolution, isPrivate, tags)
                    videoModel.isSelected = videoData.value?.find { it.id == videoModel.id }?.isSelected
                            ?: false
                    video += videoModel
                }
            }
        }
        canLoad = false
        loadingIndicatorData.value = false
        return video
    }
}
