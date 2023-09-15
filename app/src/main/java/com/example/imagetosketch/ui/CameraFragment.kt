package com.example.imagetosketch.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imagetosketch.databinding.FragmentCameraBinding
import java.io.File

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var imageUri: Uri
    private var imageCapture: ImageCapture? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        binding.cameraImageCaptureBtn.setOnClickListener {
            captureImage()
            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_LONG).show()
        }
        binding.cameraCloseButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cameraImageSendBtn.setOnClickListener {
            val action =
                CameraFragmentDirections.actionCameraFragmentToEditImageFragment(imageUri.toString())
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openCamera()
    }

    private fun openCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Set up the Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            // Set up the ImageCapture
            imageCapture = ImageCapture.Builder()
                .build()

            // Select the back camera as the default camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind any previous use cases before rebinding
                cameraProvider.unbindAll()

                // Bind the use cases to the camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

                // Show the PreviewView and hide the camera button
                binding.previewView.visibility = View.VISIBLE

            } catch (exc: Exception) {
                // Handle exceptions
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun captureImage() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            requireActivity().externalMediaDirs.firstOrNull(),
            "getCurrentTime()" + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(exc: ImageCaptureException) {
                    // Handle the image capture error
                    Toast.makeText(requireContext(), exc.message, Toast.LENGTH_LONG).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // Image capture is successful, get the saved image URI
                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    imageUri = savedUri
                    // Use the saved URI as needed
                    binding.previewView.visibility = View.GONE
                    binding.cameraImageCaptureBtn.visibility = View.GONE
                    binding.cameraImageSendBtn.visibility = View.VISIBLE
                    binding.camaraCapturedImage.apply {
                        visibility = View.VISIBLE
                        setImageURI(savedUri)
                    }
                }
            }
        )
    }

}