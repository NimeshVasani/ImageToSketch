package com.example.imagetosketch.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.imagetosketch.R
import com.example.imagetosketch.databinding.FragmentEditImageBinding


class EditImageFragment : Fragment() {

    private lateinit var bindind: FragmentEditImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentEditImageBinding.inflate(inflater, container, false)
        val data : String? = arguments?.getString("MyArgument") ?: arguments?.getString("imageUri")
        bindind.editImgView.setImageURI(Uri.parse(data))

        return bindind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext()).setMessage("are you sure ?")
                    .setNegativeButton("Cancel") { _, _ ->

                    }.setPositiveButton("Exit") { dialog, _ ->
                        dialog.dismiss()
                        findNavController().popBackStack()
                    }.show()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}