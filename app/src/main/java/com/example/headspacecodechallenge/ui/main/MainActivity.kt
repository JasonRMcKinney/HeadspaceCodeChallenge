package com.example.headspacecodechallenge.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.example.headspacecodechallenge.ui.listener.ImageItemClickListener
import com.example.headspacecodechallenge.viewmodel.MainViewModel
import com.example.headspacecodechallenge.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_photo.*


class MainActivity : AppCompatActivity(), ImageItemClickListener {

    lateinit var viewModel: MainViewModel
    lateinit var imageAdapter: ImageAdapter
    private var imageDatabase: ImageDatabase? = null
    private var pageCounter = 1

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
                is MainViewModel.AppState.SUCCESS -> displayImages(appState.imageList)
                is MainViewModel.AppState.ERROR -> displayMessage(appState.message)
                is MainViewModel.AppState.LOADING -> displayLoading()
                is MainViewModel.AppState.EMPTY -> displayEmpty()
            }
        })

        if (!viewModel.loaded) {
            viewModel.getImages(pageCounter)
        }
    }

    private fun displayLoading() {
        progressBar.visibility = View.VISIBLE
        rvImages.visibility = View.GONE
        containerMessage.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.getImages(pageCounter)
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        if (imageAdapter.itemCount > 0) {
            Toast.makeText(this, "No New Items were added", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(this, "There are no New Items to add", Toast.LENGTH_LONG)
                .show()
        }
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
        pageCounter++
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
        imageAdapter = ImageAdapter(mutableListOf(), this)
        rvImages.adapter = imageAdapter
    }

    override fun onItemClick() {
        if (imageAuthor.visibility == View.GONE) {
            imageAuthor.visibility = View.VISIBLE
            imageDimensions.visibility = View.VISIBLE
        } else {
            imageAuthor.visibility = View.GONE
            imageDimensions.visibility = View.GONE
        }
    }
}
