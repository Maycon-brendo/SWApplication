package com.example.swapplication.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.nav(id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navup() {
    findNavController().navigateUp()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}