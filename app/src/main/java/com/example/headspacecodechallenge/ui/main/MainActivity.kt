package com.example.headspacecodechallenge.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headspacecodechallenge.R
import com.example.headspacecodechallenge.db.ImageDatabase
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.network.WebService
import com.example.headspacecodechallenge.repository.ImageRepositoryImpl
import com.example.headspacecodechallenge.ui.adapter.ImageAdapter
import com.example.headspacecodechallenge.viewmodel.MainViewModel
import com.example.headspacecodechallenge.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var imageAdapter: ImageAdapter
    private var imageDatabase: ImageDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        createDbInstance()

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(ImageRepositoryImpl(imageDatabase, WebService.instance))
        ).get(MainViewModel::class.java)

        viewModel.state.observe(this, Observer { appState ->
            when (appState) {
                is MainViewModel.AppState.EMPTY -> displayEmpty()
                is MainViewModel.AppState.SUCCESS -> displayImages(appState.imageList)
                is MainViewModel.AppState.ERROR -> displayMessage(appState.message)
            }
        })

        if (!viewModel.loaded) {
            viewModel.getImages()
        }
    }

    private fun displayEmpty() {
        //flicker elimination
        imageAdapter.images.clear()
        imageAdapter.notifyDataSetChanged()
        //set UI Visibilities
        progressBar.visibility = View.GONE
        rvImages.visibility = View.VISIBLE
        containerMessage.visibility = View.GONE
        Toast.makeText(this, "There are no Items to display", Toast.LENGTH_LONG)
            .show()
    }

    private fun displayImages(images: List<ImageEntry>) {
        //flicker elimination
        imageAdapter.images.clear()
        imageAdapter.images.addAll(images)
        imageAdapter.notifyDataSetChanged()
        //set UI visiblities
        progressBar.visibility = View.GONE
        rvImages.visibility = View.VISIBLE
        containerMessage.visibility = View.GONE
        Toast.makeText(this, "There are no Items to display", Toast.LENGTH_LONG)
            .show()
    }

    private fun displayMessage(message: String) {
        // set correct visible element
        progressBar.visibility = View.GONE
        rvImages.visibility = View.GONE
        containerMessage.visibility = View.VISIBLE
        //set message
        messageText.text = message
    }

    private fun createDbInstance() {
        imageDatabase = ImageDatabase.getInstance(application)
    }

    private fun initRecyclerView() {
        rvImages.layoutManager = LinearLayoutManager(this)
        imageAdapter = ImageAdapter(mutableListOf())
        rvImages.adapter = imageAdapter
    }
}
