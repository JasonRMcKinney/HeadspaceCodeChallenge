package com.example.headspacecodechallenge.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

class MainViewModel constructor(private val imageRepository: ImageRepository) : ViewModel() {
    val state = MutableLiveData<AppState>(AppState.LOADING)
    var loaded = false

    fun getImages(page: Int) {
        state.value = AppState.LOADING
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val webImages = imageRepository.webImages(page)


                    if (webImages.isEmpty() && !loaded) {
                        Log.d("EMPTY", "Loaded = $loaded")
                        loaded = true
                        state.value = AppState.EMPTY
                    } else {
                        loaded = true
                        for (i in 0 until webImages.size - 1) {
                            val entry = ImageEntry().getImageEntryFromResponse(webImages[i])
                            val insertResult = imageRepository.insertImage(entry)
                            Log.d("INSERTS", "INSERT $i Result = $insertResult Entry = $entry")
                        }
                        val allImages = imageRepository.allImages()
                        withContext(Dispatchers.Main) { state.value = AppState.SUCCESS(allImages) }
                    }
                } catch (e: HttpException) {
                    loaded = true
                    val errorString = when (e) {
                        is UnknownHostException -> "No Internet Connection"
                        else -> e.localizedMessage
                    }
                    state.value = AppState.ERROR(errorString)
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        state.value = AppState.ERROR("Check Your Network Connection")
                    }
                }

            }
        }
    }

    sealed class AppState {
        object EMPTY : AppState()
        object LOADING : AppState()
        data class SUCCESS(val imageList: List<ImageEntry>) : AppState()
        data class ERROR(val message: String) : AppState()
    }
}


