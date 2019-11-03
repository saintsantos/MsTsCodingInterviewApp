package com.saintsantos.mstscodinginterview

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

abstract class CameraPermissionFragment: Fragment() {
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private val CAMERA_PERMISSION_REQUEST = 0

    private var permissionDeniedOnce = false
    private var paused = true

    override fun onPause() {
        super.onPause()
        paused = true
    }

    override fun onResume() {
        super.onResume()
        paused = false
    }

    fun hasCameraPermission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || ContextCompat.checkSelfPermission(requireContext(), CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestCameraPermission() {
        if (!hasCameraPermission()) {
            if (!permissionDeniedOnce) {
                requestPermissions(arrayOf(CAMERA_PERMISSION), CAMERA_PERMISSION_REQUEST)
            }

        } else {
            onCameraPermissionGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionDeniedOnce = false
                if (!paused) {
                    onCameraPermissionGranted()
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    abstract fun onCameraPermissionGranted()
}