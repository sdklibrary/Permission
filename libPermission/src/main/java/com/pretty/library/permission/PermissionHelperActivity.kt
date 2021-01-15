package com.pretty.library.permission

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

@Suppress("DEPRECATION")
internal class PermissionHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (intent.getStringArrayExtra("PERMISSIONS") != null) {
            val permissions = intent.getStringArrayExtra("PERMISSIONS")!!
            ActivityCompat.requestPermissions(this, permissions, 100)
        } else
            finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (100 == requestCode)
            PermissionManager.manager?.requestPermissionsResult(permissions)
        finish()
    }
}