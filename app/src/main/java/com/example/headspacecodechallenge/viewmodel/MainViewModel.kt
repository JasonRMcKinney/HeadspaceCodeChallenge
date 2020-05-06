package com.example.headspacecodechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.repository.ImageRepository
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class MainViewModel constructor(private val imageRepository: ImageRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    val state = MutableLiveData<AppState>()
    var loaded = false

    fun getImages() {
        state.value = AppState.LOADING
        disposable.add(
            imageRepository.webImages().subscribe({
                loaded = true
                if (it.imageItems.isEmpty()) {
                    state.value = AppState.ERROR("No Articles Retrieved")
                } else {
                    for (i in 0..it.imageItems.size) {
                        val entry = ImageEntry().getImageEntryFromResponse(it.imageItems[i])
                        imageRepository.insertImage(entry)
                    }
                    state.value = AppState.SUCCESS(imageRepository.allImages())
                }
            }, {
                loaded = true
                val errorString = when (it) {
                    is UnknownHostException -> "No Internet Connection"
                    else -> it.localizedMessage
                }
                state.value = AppState.ERROR(errorString)
            })
        )
    }

    sealed class AppState {
        data class EMPTY(val imageList) : AppState()
        object LOADING : AppState()
        data class SUCCESS(val imageList: List<ImageEntry>) : AppState()
        data class ERROR(val message: String) : AppState()
    }
}