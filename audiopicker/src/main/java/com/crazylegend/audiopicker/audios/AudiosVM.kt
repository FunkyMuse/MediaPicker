package com.crazylegend.audiopicker.audios

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crazylegend.audiopicker.listeners.onShouldRecycleBitmaps
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
internal class AudiosVM(application: Application) : AbstractAVM(application) {

    private val audioData = MutableLiveData<List<AudioModel>>()
    val audio: LiveData<List<AudioModel>> = audioData

    var onShouldRecycleBitmaps: onShouldRecycleBitmaps? = null

    override fun onCleared() {
        onShouldRecycleBitmaps?.keepItClean()
        super.onCleared()
    }

    fun loadAudios(sortOrder: SortOrder = SortOrder.DATE_ADDED_DESC) {
        if (canLoad) {
            viewModelScope.launch {
                audioData.postValue(queryAudios(sortOrder))
                initializeContentObserver(sortOrder)
            }
        }
    }

    private fun initializeContentObserver(sortOrder: SortOrder) {
        if (contentObserver == null) {
            contentObserver = contentResolver.registerObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                canLoad = true
                loadAudios(sortOrder)
            }
        }
    }

    private suspend fun queryAudios(order: SortOrder): List<AudioModel> {
        loadingIndicatorData.value = true
        val audio = mutableListOf<AudioModel>()

        val sortOrder = when (order) {
            SortOrder.DATE_ADDED_DESC -> "${MediaStore.Audio.Media.DATE_ADDED} DESC"
            SortOrder.DATE_ADDED_ASC -> "${MediaStore.Audio.Media.DATE_ADDED} ASC"

            SortOrder.DISPLAY_NAME_DESC -> "${MediaStore.Audio.Media.DISPLAY_NAME} DESC"
            SortOrder.DISPLAY_NAME_ASC -> "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

            SortOrder.DATE_MODIFIED_DESC -> "${MediaStore.Audio.Media.DATE_MODIFIED} DESC"
            SortOrder.DATE_MODIFIED_ASC -> "${MediaStore.Audio.Media.DATE_MODIFIED} ASC"

            SortOrder.SIZE_DESC -> "${MediaStore.Audio.Media.SIZE} DESC"
            SortOrder.SIZE_ASC -> "${MediaStore.Audio.Media.SIZE} ASC"
        }
        withContext(Dispatchers.IO) {
            val projection =
                    arrayOf(
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.DATE_ADDED,
                            MediaStore.Audio.Media.DATE_MODIFIED,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.WIDTH,
                            MediaStore.Audio.Media.HEIGHT,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.IS_NOTIFICATION,
                            MediaStore.Audio.Media.IS_ALARM,
                            MediaStore.Audio.Media.IS_RINGTONE,
                            MediaStore.Audio.Media.YEAR,
                            MediaStore.Audio.Media.TRACK,
                            MediaStore.Audio.Media.COMPOSER,
                            MediaStore.Audio.Media.ARTIST
                    )



            contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder)?.use { cursor ->

                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val dateAddedColumn = cursor.getSafeColumn(MediaStore.Audio.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getSafeColumn(MediaStore.Audio.Media.DATE_MODIFIED)
                val sizeColumn = cursor.getSafeColumn(MediaStore.Audio.Media.SIZE)
                val widthColumn = cursor.getSafeColumn(MediaStore.Audio.Media.WIDTH)
                val heightColumn = cursor.getSafeColumn(MediaStore.Audio.Media.HEIGHT)
                val albumColumn = cursor.getSafeColumn(MediaStore.Audio.Media.ALBUM)
                val isNotificationColumn = cursor.getSafeColumn(MediaStore.Audio.Media.IS_NOTIFICATION)
                val isAlarmColumn = cursor.getSafeColumn(MediaStore.Audio.Media.IS_ALARM)
                val isRingtoneColumn = cursor.getSafeColumn(MediaStore.Audio.Media.IS_RINGTONE)
                val yearColumn = cursor.getSafeColumn(MediaStore.Audio.Media.YEAR)
                val trackColumn = cursor.getSafeColumn(MediaStore.Audio.Media.TRACK)
                val composerColumn = cursor.getSafeColumn(MediaStore.Audio.Media.COMPOSER)
                val artistColumn = cursor.getSafeColumn(MediaStore.Audio.Media.ARTIST)


                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getStringOrNull(displayNameColumn)
                    val dateAdded = dateAddedColumn?.let { cursor.getLongOrNull(it) }
                    val dateModified = dateModifiedColumn?.let { cursor.getLongOrNull(it) }
                    val size = sizeColumn?.let { cursor.getIntOrNull(it) }
                    val width = widthColumn?.let { cursor.getIntOrNull(it) }
                    val height = heightColumn?.let { cursor.getIntOrNull(it) }
                    val album = albumColumn?.let { cursor.getStringOrNull(it) }
                    val composer = composerColumn?.let { cursor.getStringOrNull(it) }
                    val artist = artistColumn?.let { cursor.getStringOrNull(it) }
                    val isNotification = isNotificationColumn?.let { cursor.getIntOrNull(it) } != 0
                    val isAlarm = isAlarmColumn?.let { cursor.getIntOrNull(it) } != 0
                    val isRingtone = isRingtoneColumn?.let { cursor.getIntOrNull(it) } != 0
                    val year = yearColumn?.let { cursor.getIntOrNull(it) }
                    val track = trackColumn?.let { cursor.getIntOrNull(it) }

                    val contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                    val audioModel = AudioModel(id, displayName, dateAdded, contentUri, dateModified, null, size, width, height,
                            album, composer, artist, isNotification, isAlarm, isRingtone, track, year, thumbnail = loadThumbnail(
                            contentResolver, contentUri, id
                    ))
                    audioModel.isSelected = audioData.value?.find { it.id == audioModel.id }?.isSelected
                            ?: false
                    audio += audioModel
                }
            }
        }
        canLoad = false
        loadingIndicatorData.value = false
        return audio
    }

}