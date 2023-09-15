package com.example.imagetosketch.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imagetosketch.R
import com.example.imagetosketch.databinding.FragmentHomeScreenBinding
import com.google.android.material.snackbar.Snackbar


class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding

    companion object {
        var isBitmap = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

                if (uri != null) {
                    val action =
                        HomeScreenFragmentDirections.actionHomeScreenFragmentToEditImageFragment(uri.toString())
                    findNavController().navigate(action)
                } else {
                    Snackbar.make(binding.root, "Please Select an image ", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        binding.homeGalleryCard.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.homeCameraCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_cameraFragment)
        }

        binding.homeFilesCard.setOnClickListener {
            dispatchSelectFromFileIntent()
        }

        return binding.root
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val action =
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToEditImageFragment(data?.data.toString())
                findNavController().navigate(action)
            }
        }


    private fun dispatchSelectFromFileIntent() {
        val filesIntent = Intent().apply {
            type = "image/*"
            action = "android.intent.action.PICK"
        }
        isBitmap = false
        try {
            resultLauncher.launch(filesIntent)
        } catch (e: ActivityNotFoundException) {
            //
        }
    }


}